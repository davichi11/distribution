package com.distribution.modules.api.controller;

import com.alibaba.fastjson.JSON;
import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.account.entity.MemberAccount;
import com.distribution.modules.account.entity.MemberAccountHistory;
import com.distribution.modules.account.service.MemberAccountHistoryService;
import com.distribution.modules.account.service.MemberAccountService;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.pojo.vo.DisFansVO;
import com.distribution.modules.dis.entity.CardOrderInfoEntity;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.CardOrderInfoService;
import com.distribution.modules.dis.service.DisFansService;
import com.distribution.modules.dis.service.DisMemberInfoService;
import com.distribution.modules.memeber.entity.WithdrawalInfo;
import com.distribution.modules.memeber.service.WithdrawalInfoService;
import com.distribution.queue.LevelUpSender;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @Description TODO(描述)
 * @create 2018/5/11-23:09
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiMemberController {
    @Autowired
    private DisFansService disFansService;
    @Autowired
    private DisMemberInfoService disMemberInfoService;
    @Autowired
    private LevelUpSender levelUpSender;
    @Autowired
    private WithdrawalInfoService withdrawalInfoService;
    @Autowired
    private MemberAccountService memberAccountService;
    @Autowired
    private MemberAccountHistoryService memberAccountHistoryService;
    @Autowired
    private CardOrderInfoService cardOrderInfoService;

    /**
     * 锁粉接口
     *
     * @param disFansVO
     * @return
     */
    @AuthIgnore
    @PostMapping("disFans")
    @ApiOperation(value = "锁粉接口")
    @ApiImplicitParam(paramType = "body", dataType = "DisFansVO", name = "disFansVO", value = "锁粉信息", required = true)
    public Result lockFans(@RequestBody DisFansVO disFansVO) {
        if (StringUtils.isBlank(disFansVO.getWechatId())) {
            return Result.error("微信openID为空");
        }
        if (StringUtils.isBlank(disFansVO.getMemberId())) {
            return Result.error("推荐人ID为空");
        }
        DisFans disFans = new DisFans();
        BeanUtils.copyProperties(disFansVO, disFans);
        disFans.setId(CommonUtils.getUUID());
        //查询推荐人是否存在
        DisMemberInfoEntity disMemberInfo = disMemberInfoService.queryObject(disFansVO.getMemberId());
        if (disMemberInfo == null) {
            return Result.error("推荐人ID不存在");
        }
        //关联推荐人
        disFans.setDisMemberInfo(disMemberInfo);
        try {
            disFansService.save(disFans);
        } catch (Exception e) {
            log.error("保存锁粉信息异常", e);
            return Result.error("保存锁粉信息异常");
        }
        levelUpSender.send(JSON.toJSONString(disMemberInfo));
        return Result.ok("锁粉成功");
    }

    /**
     * 用户总金额，提现，总收入
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 19:54
     * @Description:
     */
    @AuthIgnore
    @GetMapping("/MemberAmount")
    @ApiOperation(value = "用户总金额，提现，总收入")
    public Result MemberAmount(@RequestParam String userId,@RequestParam String mobile) {
        Map<String, Object> resultMap = new HashMap<>();
//        Map<String, Object> param = new HashMap<>(2);
//        param.put("userId",userId);
        //提现金额
        BigDecimal withdrawalAmount = withdrawalInfoService.withdrawalAmounts(mobile);
        resultMap.put("withdrawalAmount", withdrawalAmount);
        resultMap.put("memberAccount", 0.00);
        resultMap.put("generalIncome", 0.00);
        //账户信息
        MemberAccount memberAccount = memberAccountService.selectMemberAccountByUserId(userId);
        if (memberAccount != null) {
            //账户余额
            resultMap.put("memberAccount", memberAccount.getMemberAmount());
            Map<String, Object> map = new HashMap<>();
            map.put("accountId", memberAccount.getAccountId());
            map.put("hisType", MemberAccountHistory.HisType.INCOME);
            map.put("userId", userId);
            List<MemberAccountHistory> list = memberAccountHistoryService.findList(map);
            Double generalIncome = 0.00;
            if (null != list && list.size() > 0) {
                for (MemberAccountHistory memberAccountHistory : list) {
                    generalIncome = memberAccountHistory.getHisAmount().add(new BigDecimal(generalIncome)).doubleValue();
                }
            }
            //收入总金额
            resultMap.put("generalIncome", generalIncome);
        }

        return Result.ok(resultMap);
    }


    /**
     * 提现记录
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 20:05
     * @Description:
     */
    @AuthIgnore
    @GetMapping("/MemberWithdrawalList")
    @ApiOperation(value = "提现记录")
    public Result MemberWithdrawalList(@RequestParam String mobile) {
        List<WithdrawalInfo> withdrawalInfoList = withdrawalInfoService.queryList(mobile);
        return Result.ok().put("MemberWithdrawalList", withdrawalInfoList);
    }


    /**
     * 收益明细
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 20:06
     * @Description:
     */
    @AuthIgnore
    @GetMapping("/memberAmountHistroyList")
    @ApiOperation(value = "收益明细")
    public Result MemberReturns(@RequestParam String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        List<MemberAccountHistory> memberAmountHistroyList = memberAccountHistoryService.findList(map);
        return Result.ok().put("MemberWithdrawalList", memberAmountHistroyList);
    }

    /**
     * 用户银行卡列表
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 20:40
     * @Description:
     */
    @AuthIgnore
    @GetMapping("/MemberCardList")
    @ApiOperation(value = "用户银行卡列表")
    public Result MemberCardList(@RequestParam String userId) {
        Map<String, Object> map = new HashMap<>();
        List<CardOrderInfoEntity> MemberCardList = new ArrayList<>();
        map.put("userId",userId);
        List<DisMemberInfoEntity> memberInfoEntities = disMemberInfoService.queryList(map);
        if (null != memberInfoEntities && memberInfoEntities.size() > 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("memberId", memberInfoEntities.get(0).getId());
//            param.put("orderStatus", CardOrderInfoEntity.OrderStatus.SUCCESS);
            MemberCardList = cardOrderInfoService.queryList(param);
        }
        return Result.ok().put("MemberCardList",MemberCardList);
    }

    /**
     *    添加提现记录
     *
     * @author liuxinxin
     * @date  10:45
     * @param
     * @return
     */
    @AuthIgnore
    @PostMapping("/saveWithdrawalInfo")
    @ApiOperation(value = "添加提现记录")
    public Result saveWithdrawalInfo(@RequestBody WithdrawalInfo withdrawalInfo) {
        try {
            withdrawalInfo.setId(CommonUtils.getUUID());
            withdrawalInfo.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
            withdrawalInfoService.save(withdrawalInfo);
        } catch (Exception e) {
            return Result.error("提现异常");
        }
        return Result.ok();
    }

    /**
     *
     *   查询用户信息
     * @author liuxinxin
     * @date  11:40
     * @param
     * @return
     */
    @AuthIgnore
    @GetMapping("/disMember")
    @ApiOperation(value = "查询用户信息")
    public Result disMember(@RequestParam String userId){
        Map<String,Object> map=new HashMap<>(2);
        map.put("userId",userId);
        List<DisMemberInfoEntity> disMemberInfoEntities = disMemberInfoService.queryList(map);
        if (null!=disMemberInfoEntities&&disMemberInfoEntities.size()>0){
            return Result.ok().put("disMember",disMemberInfoEntities.get(0));
        }else {
            return Result.error("没有查询到用户信息");
        }
    }

}
