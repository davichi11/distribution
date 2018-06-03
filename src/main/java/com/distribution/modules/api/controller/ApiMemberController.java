package com.distribution.modules.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
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
import com.distribution.modules.api.pojo.vo.WithdrawalVo;
import com.distribution.modules.dis.entity.CardOrderInfoEntity;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.CardOrderInfoService;
import com.distribution.modules.dis.service.DisFansService;
import com.distribution.modules.dis.service.DisMemberInfoService;
import com.distribution.modules.memeber.entity.WithdrawalInfo;
import com.distribution.modules.memeber.service.WithdrawalInfoService;
import com.distribution.modules.sys.service.SysConfigService;
import com.distribution.queue.LevelUpSender;
import com.distribution.weixin.service.WeiXinService;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
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
    private SysConfigService configService;
    @Autowired
    private WeiXinService weiXinService;
    @Autowired
    private ModelMapper modelMapper;

    @Value("{risk.url}")
    private String renturnUrl;

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
     * 创建用户账户信息
     * @param mobile
     * @param alipayAccount
     * @return
     */
    @ApiOperation(value = "创建用户账户信息")
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
        MemberAccount memberAccount = new MemberAccount();
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
        //提现金额
        BigDecimal withdrawalAmount = withdrawalInfoService.withdrawalAmounts(mobile);
        resultMap.put("withdrawalAmount", withdrawalAmount);
        resultMap.put("memberAccount", 0.00);
        resultMap.put("generalIncome", 0.00);
        //账户信息
        MemberAccount memberAccount = memberAccountService.selectMemberAccountByUserId(mobile);
        if (memberAccount != null) {
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
    public Result memberReturns(@PathVariable String mobile) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("mobile", mobile);
        DisMemberInfoEntity member = disMemberInfoService.queryList(map).stream().findFirst().orElse(new DisMemberInfoEntity());
        map.put("userId", member.getId());
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
    public Result memberCardList(@RequestParam String mobile) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("mobile", mobile);
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
    public Result saveWithdrawalInfo(@RequestBody WithdrawalVo withdrawalVo) {
        //提现校验
        MemberAccount account = memberAccountService.selectMemberAccountByUserId(withdrawalVo.getWithdrawMobile());
        if (account.getMemberAmount().compareTo(withdrawalVo.getWithdrawAmount()) < 0) {
            return Result.error("提现金额超出可用余额");
        }

        String orderId = AliPayUtil.generateOrderId(withdrawalVo.getWithdrawMobile(),
                Constant.PayType.WITHDRAWAL.getValue());
        AliPayParams payParams = new AliPayParams();
        payParams.setAmount(withdrawalVo.getWithdrawAmount().toString());
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
            AlipayFundTransToaccountTransferResponse response = AliPayUtil.transferResponse(payParams);
            if (!response.isSuccess()) {
                log.error("会员{}提现失败,case:{},订单号为{}", account.getMember().getDisUserName(),
                        response.getMsg(), orderId);
            }
            //保存提现记录
            WithdrawalInfo withdrawalInfo = modelMapper.map(withdrawalVo, WithdrawalInfo.class);
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
    @AuthIgnore
    @GetMapping("/disMember/{mobile}")
    @ApiOperation(value = "查询用户信息")
    public Result disMember(@PathVariable("mobile") String mobile) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("mobile", mobile);
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
     * @param mobile
     * @param memberVO
     * @return
     */
    @ApiOperation("更新会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "mobile", value = "手机号", required = true)
    })
    @PatchMapping("/disMember/{mobile}")
    public Result updateMemberInfo(@PathVariable("mobile") String mobile, @RequestBody DisMemberVO memberVO) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("mobile", mobile);
        DisMemberInfoEntity member = disMemberInfoService.queryList(param).stream().findFirst()
                .orElse(new DisMemberInfoEntity());
        BeanUtils.copyProperties(memberVO, member);
        try {
            disMemberInfoService.update(member);
        } catch (Exception e) {
            log.error("更新会员信息异常", e);
            return Result.error("更新会员信息异常");
        }
        return Result.ok("更新成功");
    }

    /**
     * 购买会员
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 14:25
     */
    @GetMapping("/checkMemberDisLevel")
    @ApiOperation(value = "购买会员")
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
        Map<String, Object> json = new HashMap<>(2);
        json.put("level", disLevel);
        //获取会员升级价格配置
        String price = configService.getValue("level_price", "");
        JSONObject priceJson = JSON.parseObject(price);
        String amount = priceJson.getString("level_" + disLevel);
        //校验通过 生成APP支付订单
        String orderNo = AliPayUtil.generateOrderId(memberId, "1");
        //构造支付请求参数
        AliPayParams payParams = new AliPayParams();
        payParams.setSubject("会员升级服务");
        payParams.setOutTradeNo(orderNo);
        payParams.setTotalAmount(amount);
        payParams.setQuitUrl(renturnUrl);
        try {
            payParams.setPassbackParams(URLEncoder.encode(JSON.toJSONString(json), "UTF-8"));
            AlipayTradeWapPayResponse wapPayResponse = AliPayUtil.tradeWapPay(payParams);
            if (wapPayResponse.isSuccess()) {
                return Result.ok("支付成功");
            } else {
                return Result.error("支付失败");
            }
        } catch (Exception e) {
            log.error("生成APP支付订单异常", e);
            return Result.error("生成APP支付订单异常");
        }
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
                String level = JSON.parseObject(URLDecoder.decode(params.get("passbackParams"), "UTF-8")).getString("level");
                if (StringUtils.isBlank(level)) {
                    return Result.error("回传参数中没有会员等级");
                }
                //根据支付宝账户查询对应的会员信息
                DisMemberInfoEntity member = memberAccountService.selectByAlipay(params.get("buyer_logon_id")).getMember();
                //构造模板消息
                WxMpTemplateMessage templateMessage = buildTemplateMsg(member.getOpenId(), member.getDisLevel().toString(),
                        level, member.getDisUserName());
                //升级会员
                member.setDisLevel(NumberUtils.toInt(level));
                //用户类型升级为会员
                if ("0".equals(member.getDisUserType())) {
                    member.setDisUserType("1");
                }
                disMemberInfoService.update(member);
                //生级成功发送消息
                weiXinService.sendTemplateMsg(templateMessage);
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

    /**
     * 组装会员升级提醒模板消息
     *
     * @param openId
     * @param oldLevel
     * @param newLevel
     * @param name
     * @return
     */
    private WxMpTemplateMessage buildTemplateMsg(String openId, String oldLevel, String newLevel, String name) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("kfKckhHo6GnTqO35SPHgQYKkomi1HV9kXsGbm9AO2Y8");
        wxMpTemplateMessage.setToUser(openId);
        List<WxMpTemplateData> templateDataList = Lists.newArrayList(
                new WxMpTemplateData("first", MessageFormat.format("尊敬的会员：{0}，您的会员已经成功开通！", name)),
                new WxMpTemplateData("keyword1", oldLevel),
                new WxMpTemplateData("keyword2", newLevel),
                new WxMpTemplateData("keyword3", " "),
                new WxMpTemplateData("remark", "感谢您的使用")
        );
        wxMpTemplateMessage.setData(templateDataList);
        return wxMpTemplateMessage;
    }

}
