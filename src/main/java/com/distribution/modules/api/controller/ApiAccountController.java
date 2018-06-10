package com.distribution.modules.api.controller;

import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.distribution.ali.pay.AliPayParams;
import com.distribution.ali.pay.AliPayUtils;
import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.Constant;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.account.entity.MemberAccount;
import com.distribution.modules.account.entity.MemberAccountHistory;
import com.distribution.modules.account.service.MemberAccountHistoryService;
import com.distribution.modules.account.service.MemberAccountService;
import com.distribution.modules.api.config.JWTConfig;
import com.distribution.modules.api.pojo.vo.WithdrawalVo;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.DisMemberInfoService;
import com.distribution.modules.memeber.entity.WithdrawalInfo;
import com.distribution.modules.memeber.service.WithdrawalInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @Description TODO(描述)
 * @create 2018/6/8-17:28
 */
@Api("用户账户接口")
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiAccountController {
    @Autowired
    private DisMemberInfoService disMemberInfoService;
    @Autowired
    private WithdrawalInfoService withdrawalInfoService;
    @Autowired
    private MemberAccountService memberAccountService;
    @Autowired
    private MemberAccountHistoryService memberAccountHistoryService;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JWTConfig jwtConfig;
    @Value("{risk.url}")
    private String returnUrl;

    /**
     * 创建用户账户信息
     *
     * @param mobile
     * @param alipayAccount
     * @return
     */
    @ApiOperation(value = "创建或更新用户账户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "mobile", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "alipay_account", value = "支付宝账户", required = true),
    })
    @PostMapping("/memberAccount/{mobile}")
    public Result memberAccount(@PathVariable("mobile") String mobile, @RequestParam("alipay_account") String alipayAccount) {
        if (StringUtils.isBlank(alipayAccount)) {
            return Result.error("支付宝账户不能为空");
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("mobile", mobile);
        DisMemberInfoEntity member = disMemberInfoService.queryList(map).stream().findFirst().orElse(new DisMemberInfoEntity());

        MemberAccount memberAccount = memberAccountService.selectMemberAccountByUserId(mobile);
        if (memberAccount == null) {
            memberAccount = new MemberAccount();
            memberAccount.setMember(member);
            memberAccount.setMemberType("1");
            memberAccount.setAliPayAccount(alipayAccount);
            memberAccount.setIsDelete("1");
            memberAccount.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
            memberAccount.setAccountId(CommonUtils.getUUID());
            memberAccount.setMemberAmount(new BigDecimal(0));
            try {
                memberAccountService.save(memberAccount);
                return Result.ok("用户账户信息保存成功");
            } catch (Exception e) {
                log.error("保存用户账户信息异常", e);
                return Result.error("保存用户账户信息异常");
            }
        } else {
            memberAccount.setAliPayAccount(alipayAccount);
            try {
                memberAccountService.update(memberAccount);
                return Result.ok("用户账户信息更新成功");
            } catch (Exception e) {
                log.error("保存用户账户信息异常", e);
                return Result.error("更新用户账户信息异常");
            }
        }
    }

    @ApiOperation(value = "用户支付宝账户更新")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "mobile", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "alipay_account", value = "支付宝账户", required = true),
    })
    @PatchMapping("/memberAccount/{mobile}")
    public Result updateAliPayAccount(@PathVariable("mobile") String mobile, @RequestParam("alipay_account") String alipayAccount) {
        if (StringUtils.isBlank(alipayAccount)) {
            return Result.error("支付宝账户不能为空");
        }
        MemberAccount memberAccount = memberAccountService.selectMemberAccountByUserId(mobile);
        memberAccount.setAliPayAccount(alipayAccount);
        try {
            memberAccountService.update(memberAccount);
            return Result.ok("用户支付宝账户更新成功");
        } catch (Exception e) {
            log.error("用户支付宝账户更新异常", e);
            return Result.error("用户支付宝账户更新异常");
        }
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
    public Result memberAmount(@RequestParam String mobile) {
        Map<String, Object> resultMap = new HashMap<>(6);
//        Map<String, Object> param = new HashMap<>(2);
//        param.put("userId",userId);
        //账户信息
        MemberAccount memberAccount = memberAccountService.selectMemberAccountByUserId(mobile);
        if (memberAccount == null) {
            return Result.error("请先设置账户信息");
        }
        //提现金额
        BigDecimal withdrawalAmount = withdrawalInfoService.withdrawalAmounts(mobile);
        resultMap.put("withdrawalAmount", withdrawalAmount);
        //账户余额
        resultMap.put("memberAccount", memberAccount.getMemberAmount());
        Map<String, Object> map = new HashMap<>(6);
        map.put("accountId", memberAccount.getAccountId());
        map.put("hisType", MemberAccountHistory.HisType.INCOME);
        List<MemberAccountHistory> list = memberAccountHistoryService.findList(map);
        Double generalIncome = 0.00;
        if (null != list && list.size() > 0) {
            generalIncome = list.stream().mapToDouble(value -> value.getHisAmount().doubleValue()).sum();
        }
        //收入总金额
        resultMap.put("generalIncome", generalIncome);

        return Result.ok(resultMap);
    }


    /**
     * 提现记录
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 20:05
     * @Description:
     */
    @GetMapping("/withdrawals/{mobile}")
    @ApiOperation(value = "提现记录")
    public Result memberWithdrawalList(@PathVariable String mobile) {
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
    @GetMapping("/memberAccountHistory/{mobile}")
    @ApiOperation(value = "收益明细")
    public Result memberReturns(@PathVariable String mobile, Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("mobile", mobile);
        DisMemberInfoEntity member = disMemberInfoService.queryList(map).stream().findFirst().orElse(new DisMemberInfoEntity());
        map.put("userId", member.getId());
        map.put("hisType", MemberAccountHistory.HisType.INCOME);
        map.putAll(params);
        PageInfo<MemberAccountHistory> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo(() -> memberAccountHistoryService.findList(map));
        return Result.ok().put("memberWithdrawalList", pageInfo);
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
    public Result saveWithdrawalInfo(@RequestBody WithdrawalVo withdrawalVo) {
        //提现校验
        MemberAccount account = memberAccountService.selectMemberAccountByUserId(withdrawalVo.getWithdrawMobile());
        if (account == null) {
            return Result.error("请先设置支付宝账户");
        }
        if (account.getMemberAmount().compareTo(withdrawalVo.getWithdrawAmount()) < 0) {
            return Result.error("提现金额超出可用余额");
        }
        String countKey = withdrawalVo.getWithdrawMobile() + "_withdrawal";

        Double count = NumberUtils.toDouble(redisTemplate.opsForValue().get(countKey));
        if (count >= 500) {
            return Result.error("已超出每日提现限额");
        }

        String orderId = AliPayUtils.generateOrderId(withdrawalVo.getWithdrawMobile(),
                Constant.PayType.WITHDRAWAL.getValue());
        AliPayParams payParams = new AliPayParams();
        payParams.setAmount(withdrawalVo.getWithdrawAmount().doubleValue());
        payParams.setOutBizNo(orderId);
        payParams.setPayeeAccount(account.getAliPayAccount());
        payParams.setPayeeRealName(account.getMember().getDisUserName());
        //如果提现金额达到50000,remark必填
        if (withdrawalVo.getWithdrawAmount().compareTo(new BigDecimal(50000)) > 0) {
            payParams.setRemark(String.format("会员%s提现,金额:%s", account.getMember().getDisUserName(),
                    withdrawalVo.getWithdrawAmount()));
        }

        try {
            //调用支付宝转账接口
            AlipayFundTransToaccountTransferResponse response = AliPayUtils.transferResponse(payParams);
            if (!response.isSuccess()) {

                log.error("会员{}提现失败,case:{},订单号为{}", account.getMember().getDisUserName(),
                        response.getMsg(), orderId);
                return Result.error("提现失败");
            }
            //提现成功更新账户余额
            account.setMemberAmount(account.getMemberAmount().subtract(withdrawalVo.getWithdrawAmount()));
            memberAccountService.update(account);
            //保存提现记录
            WithdrawalInfo withdrawalInfo = modelMapper.map(withdrawalVo, WithdrawalInfo.class);
            withdrawalInfo.setWithdrawType(response.isSuccess() ? "1" : "0");
            withdrawalInfo.setWithdrawNum(orderId);
            withdrawalInfo.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
            withdrawalInfo.setId(CommonUtils.getUUID());
            withdrawalInfoService.save(withdrawalInfo);
            //当前时间
            LocalDateTime start = LocalDateTime.now();
            //第二天开始时间
            LocalDateTime end = LocalDateTime.now().plusDays(1).with(LocalDateTime.MIN);
            //计算时间差
            Duration between = Duration.between(start, end);
            //获取相差的小时
            long hours = between.toMillis();

            //更新每日提现限额
            redisTemplate.opsForValue().set(countKey, withdrawalVo.getWithdrawAmount().add(new BigDecimal(count)) + "",
                    hours, TimeUnit.MILLISECONDS);
            //发送提现成功提醒
            wxMpService.getTemplateMsgService().sendTemplateMsg(buildTemplateMsg(account.getMember().getOpenId(),
                    withdrawalVo.getWithdrawAmount().toString(), withdrawalInfo.getWithdrawName()));
        } catch (Exception e) {
            log.error("提现异常", e);
            return Result.error("提现异常");
        }
        return Result.ok();
    }

    /**
     * 构造提现提醒模板信息
     *
     * @param openId
     * @param amount
     * @param name
     * @return
     */
    private WxMpTemplateMessage buildTemplateMsg(String openId, String amount, String name) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("g-fARpDMBjuMPPnAQnUgUN1YLivLZQvhnAFaELvV_bU");
        wxMpTemplateMessage.setToUser(openId);
        List<WxMpTemplateData> templateDataList = Lists.newArrayList(
                new WxMpTemplateData("first", MessageFormat.format("提现成功！", name)),
                new WxMpTemplateData("keyword1", amount),
                new WxMpTemplateData("keyword2", DateUtils.formatDateTime(LocalDateTime.now())),
                new WxMpTemplateData("remark", "感谢您的使用")
        );
        wxMpTemplateMessage.setData(templateDataList);
        return wxMpTemplateMessage;
    }
}
