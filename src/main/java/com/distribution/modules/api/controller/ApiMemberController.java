package com.distribution.modules.api.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.distribution.ali.pay.AliPayParams;
import com.distribution.ali.pay.AliPayUtil;
import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.Constant;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.account.entity.MemberAccount;
import com.distribution.modules.account.entity.MemberAccountHistory;
import com.distribution.modules.account.service.MemberAccountHistoryService;
import com.distribution.modules.account.service.MemberAccountService;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.pojo.vo.DisFansVO;
import com.distribution.modules.api.pojo.vo.DisMemberVO;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ModelMapper modelMapper;

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
    @GetMapping("/memberAmount")
    @ApiOperation(value = "用户总金额，提现，总收入")
    public Result memberAmount(@RequestParam String userId, @RequestParam String mobile) {
        Map<String, Object> resultMap = new HashMap<>(6);
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
            Map<String, Object> map = new HashMap<>(6);
            map.put("accountId", memberAccount.getAccountId());
            map.put("hisType", MemberAccountHistory.HisType.INCOME);
            map.put("userId", userId);
            List<MemberAccountHistory> list = memberAccountHistoryService.findList(map);
            Double generalIncome = 0.00;
            if (null != list && list.size() > 0) {
                generalIncome = list.stream().mapToDouble(value -> value.getHisAmount().doubleValue()).sum();
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
    @GetMapping("/memberWithdrawalList")
    @ApiOperation(value = "提现记录")
    public Result memberWithdrawalList(@RequestParam String mobile) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("withdrawMobile", mobile);
        List<WithdrawalInfo> withdrawalInfoList = withdrawalInfoService.queryList(param);
        return Result.ok().put("memberWithdrawalList", withdrawalInfoList);
    }


    /**
     * 收益明细
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 20:06
     * @Description:
     */
    @GetMapping("/memberAmountHistroyList")
    @ApiOperation(value = "收益明细")
    public Result memberReturns(@RequestParam String userId) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        List<MemberAccountHistory> memberAmountHistroyList = memberAccountHistoryService.findList(map);
        return Result.ok().put("memberWithdrawalList", memberAmountHistroyList);
    }

    /**
     * 用户银行卡列表
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 20:40
     * @Description:
     */
    @GetMapping("/memberCardList")
    @ApiOperation(value = "用户办卡信息列表")
    public Result memberCardList(@RequestParam String userId) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        List<CardOrderInfoEntity> memberCardList = new ArrayList<>();
        List<DisMemberInfoEntity> memberInfoEntities = disMemberInfoService.queryList(map);
        if (CollectionUtils.isNotEmpty(memberInfoEntities)) {
            Map<String, Object> param = new HashMap<>(2);
            param.put("memberId", memberInfoEntities.get(0).getId());
//            param.put("orderStatus", CardOrderInfoEntity.OrderStatus.SUCCESS);
            memberCardList = cardOrderInfoService.queryList(param);
        }
        return Result.ok().put("memberCardList", memberCardList);
    }

    /**
     * 用户提现
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 10:45
     */
    @PostMapping("/withdrawal")
    @ApiOperation(value = "用户提现")
    public Result saveWithdrawalInfo(@RequestBody WithdrawalInfo withdrawalInfo) {
        //提现校验
        MemberAccount account = memberAccountService.selectMemberAccountByUserId(withdrawalInfo.getWithdrawMobile());
        if (account.getMemberAmount().compareTo(withdrawalInfo.getWithdrawAmount()) < 0) {
            return Result.error("提现金额超出可用余额");
        }

        String orderId = AliPayUtil.generateOrderId(withdrawalInfo.getWithdrawMobile(),
                Constant.PayType.WITHDRAWAL.getValue());
        AliPayParams payParams = new AliPayParams();
        payParams.setAmount(withdrawalInfo.getWithdrawAmount().toString());
        payParams.setOutBizNo(orderId);
        payParams.setPayeeAccount(account.getAliPayAccount());
        payParams.setPayeeRealName(account.getMember().getDisUserName());
        //如果提现金额达到50000,remark必填
        if (withdrawalInfo.getWithdrawAmount().compareTo(new BigDecimal(50000)) > 0) {
            payParams.setRemark(String.format("会员%s提现,金额:%s", account.getMember().getDisUserName(),
                    withdrawalInfo.getWithdrawAmount()));
        }

        try {
            //调用支付宝转账接口
            AlipayFundTransToaccountTransferResponse response = AliPayUtil.transferResponse(payParams);
            if (!response.isSuccess()) {
                log.error(String.format("会员%s提现失败,case:%s,订单号为%s", account.getMember().getDisUserName(),
                        response.getMsg(), orderId));
            }
            //保存提现记录
            withdrawalInfo.setWithdrawType(response.isSuccess() ? "1" : "0");
            withdrawalInfo.setWithdrawNum(orderId);
            withdrawalInfo.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
            withdrawalInfo.setId(CommonUtils.getUUID());
            withdrawalInfoService.save(withdrawalInfo);
        } catch (Exception e) {
            return Result.error("提现异常");
        }
        return Result.ok();
    }

    /**
     * 查询用户信息
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 11:40
     */
    @GetMapping("/disMember")
    @ApiOperation(value = "查询用户信息")
    public Result disMember(@RequestParam String userId) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        List<DisMemberInfoEntity> disMemberInfoEntities = disMemberInfoService.queryList(map);
        if (CollectionUtils.isNotEmpty(disMemberInfoEntities)) {
            return Result.ok().put("disMember", disMemberInfoEntities.get(0));
        } else {
            return Result.error("没有查询到用户信息");
        }
    }

    /**
     * 更新会员信息
     *
     * @param id
     * @param memberVO
     * @return
     */
    @PatchMapping("/disMember/{id}")
    public Result updateMemberInfo(@PathVariable("id") String id, @RequestBody DisMemberVO memberVO) {
        DisMemberInfoEntity member = modelMapper.map(memberVO, DisMemberInfoEntity.class);
        member.setId(id);
        try {
            disMemberInfoService.update(member);
        } catch (Exception e) {
            log.error("更新会员信息异常", e);
            return Result.error("更新会员信息异常");
        }
        return Result.ok("更新成功");
    }

    /**
     * 购买会员校验
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 14:25
     */
    @GetMapping("/checkMemberDisLevel")
    @ApiOperation(value = "购买会员校验")
    public Result checkMemberDisLevel(@RequestParam String memberId, @RequestParam Integer disLevel) {
        DisMemberInfoEntity disMemberInfoEntity = disMemberInfoService.queryObject(memberId);
        //非会员可以通过
        if ("1".equals(disMemberInfoEntity.getDisUserType())) {
            //如果是1级会员
            if (1 == disMemberInfoEntity.getDisLevel()) {
                return Result.error("您已是1级会员，无需升级");
            }
            //如果是2级会员
            else if (2 == disMemberInfoEntity.getDisLevel()) {
                if (1 != disLevel) {
                    return Result.error("您已是2级会员，无需升级");
                }
            }
            //如果是3级会员
            else if (3 == disMemberInfoEntity.getDisLevel()) {
                if (3 == disLevel) {
                    return Result.error("您已是3级会员，无需升级");
                }
            }
        }
        return Result.ok();
    }

    /**
     * 购买会员 支付回调
     *
     * @param request
     * @return
     */
    @PostMapping("/alipayCallback")
    public Result alipayCallback(HttpServletRequest request, Map<String, String> params) {
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        Map<String, String> params = new HashMap<>(parameterMap.size());
//        parameterMap.forEach((k, v) -> params.put(k, Arrays.stream(v).collect(Collectors.joining(","))));
        try {
            if (AliPayUtil.signVerified(params)) {
                //从回传参数中取出购买的会员等级
                String level = JSON.parseObject(URLDecoder.decode(params.get("passback_params"),"UTF-8")).getString("level");
                if (StringUtils.isBlank(level)) {
                    return Result.error("回传参数中没有会员等级");
                }
                //根据支付宝账户查询对应的会员信息
                DisMemberInfoEntity member = memberAccountService.selectByAlipay(params.get("buyer_logon_id")).getMember();
                //升级会员
                member.setDisLevel(NumberUtils.toInt(level));
                //用户类型升级为会员
                if ("0".equals(member.getDisUserType())) {
                    member.setDisUserType("1");
                }
                disMemberInfoService.update(member);
                return Result.ok();
            } else {
                return Result.error("验签失败");
            }
        } catch (AlipayApiException e) {
            log.error("回调参数验签异常", e);
            return Result.error("回调参数验签异常");
        } catch (Exception e) {
            log.error("会员升级异常", e);
            return Result.error("会员升级异常");
        }
    }

}
