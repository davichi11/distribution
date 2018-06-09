package com.distribution.modules.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.distribution.ali.pay.AliPayParams;
import com.distribution.ali.pay.AliPayUtil;
import com.distribution.common.utils.Result;
import com.distribution.modules.account.entity.MemberAccount;
import com.distribution.modules.account.service.MemberAccountHistoryService;
import com.distribution.modules.account.service.MemberAccountService;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.config.JWTConfig;
import com.distribution.modules.api.pojo.vo.DisFansVO;
import com.distribution.modules.api.pojo.vo.DisMemberVO;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.CardOrderInfoService;
import com.distribution.modules.dis.service.DisFansService;
import com.distribution.modules.dis.service.DisMemberInfoService;
import com.distribution.modules.dis.service.DisProfiParamService;
import com.distribution.modules.memeber.service.WithdrawalInfoService;
import com.distribution.modules.sys.service.SysConfigService;
import com.distribution.queue.LevelUpSender;
import com.distribution.weixin.service.WeiXinService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @Description TODO(描述)
 * @create 2018/5/11-23:09
 */
@Api("会员信息相关接口")
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiMemberController {
    @Autowired
    private DisFansService disFansService;
    @Autowired
    private DisMemberInfoService disMemberInfoService;
    @Autowired
    private DisProfiParamService profiParamService;
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
    @Autowired
    private JWTConfig jwtConfig;
    @Value("{risk.url}")
    private String returnUrl;

    /**
     * 我的团队
     *
     * @param mobile
     * @return
     */
    @GetMapping("team")
    @ApiOperation(value = "我的团队")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "会员手机号", required = true)
    public Result myTeam(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return Result.error("手机号不能为空");
        }
        DisMemberInfoEntity member = disMemberInfoService.queryByMobile(mobile);
        //查询所有锁粉信息
        Map<String, Object> fansParam = new HashMap<>(2);
        fansParam.put("memberId", member.getId());
        List<DisMemberVO> disFansList = disFansService.queryList(fansParam).stream().filter(Objects::nonNull)
                .map(disFans -> {
                    DisMemberInfoEntity memberInfo = disMemberInfoService.queryByOpenId(disFans.getWechatId());
                    return getDisMemberVO(disFans, memberInfo);
                }).collect(Collectors.toList());

        //所有代理信息
        List<DisMemberVO> children = member.getDisMemberChildren().stream().filter(Objects::nonNull).map(memberInfo -> {
            DisFans disFans = disFansService.queryByOpenId(memberInfo.getOpenId());
            return getDisMemberVO(disFans, memberInfo);
        }).collect(Collectors.toList());

        //返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("countFans", Optional.ofNullable(disFansList).orElse(new ArrayList<>()).size());
        map.put("fansList", disFansList);
        map.put("countChirldern", Optional.ofNullable(member.getDisMemberChildren()).orElse(new ArrayList<>()).size());
        map.put("children", children);
        return Result.ok().put("results", map);
    }

    @NotNull
    private DisMemberVO getDisMemberVO(DisFans disFans, DisMemberInfoEntity memberInfo) {
        DisMemberVO memberVO = new DisMemberVO();
        memberVO.setDisUserName(memberInfo.getDisUserName());
        memberVO.setDisUserType(memberInfo.getDisUserType());
        memberVO.setDisLevel(memberInfo.getDisLevel());
        memberVO.setMobile(memberInfo.getUserEntity().getMobile());
        memberVO.setOpenId(memberInfo.getOpenId());
        memberVO.setAddTime(memberInfo.getAddTime());
        memberVO.setNickName(disFans.getWechatNickname());
        memberVO.setImgUrl(disFans.getWechatImg());
        return memberVO;
    }


    @GetMapping("/memberAccount/{mobile}")
    @ApiOperation(value = "查询用户信息")
    public Result memberAccountInfo(@PathVariable("mobile") String mobile) {
        MemberAccount memberAccount = Optional.ofNullable(memberAccountService.selectMemberAccountByUserId(mobile))
                .orElse(new MemberAccount());
        return Result.ok().put("alipayAccount", memberAccount.getAliPayAccount());
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


    @AuthIgnore
    @GetMapping("/upLevel/{mobile}")
    @ApiOperation(value = "查询用户上级信息")
    public Result getUpLevel(@PathVariable("mobile") String mobile) {
        DisMemberInfoEntity up = disMemberInfoService.queryByMobile(mobile);
        if (up == null) {
            return Result.error("用户不存在");
        }
        DisFans disFans = disFansService.queryByOpenId(up.getOpenId());
        DisMemberVO memberVO = new DisMemberVO();
        memberVO.setDisUserName(up.getDisUserName());
        memberVO.setDisUserType(up.getDisLevel().toString());
        memberVO.setMobile(up.getUserEntity().getMobile());
        memberVO.setOpenId(up.getOpenId());
        memberVO.setNickName(disFans.getWechatNickname());
        memberVO.setImgUrl(disFans.getWechatImg());
        memberVO.setDisLevel(up.getDisLevel());

        return Result.ok().put("upLevel", memberVO);
    }


    @AuthIgnore
    @ApiOperation(value = "查询微信关注者信息")
    @GetMapping("/disFans")
    public Result disFansInfo(@RequestParam String openId) {
        DisFans fans = disFansService.queryByOpenId(openId);
        DisFansVO fansVO = modelMapper.map(fans, DisFansVO.class);
        DisMemberInfoEntity member = disMemberInfoService.queryByOpenId(openId);
        if (member != null) {
            fansVO.setMobile(member.getUserEntity().getMobile());
            fansVO.setToken(jwtConfig.generateToken(member.getUserEntity().getUserId()));
        }
        return Result.ok().put("weixinInfo", fans);
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

    @AuthIgnore
    @ApiOperation("获取VIP购买列表")
    @GetMapping("/vip")
    public Result getVipList() {
        String config = configService.getValue("level_price", "");
        JSONObject json = JSON.parseObject(config);
        return Result.ok().put("level", json);
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
    public Result checkMemberDisLevel(@RequestParam String mobile, @RequestParam Integer disLevel) {
        DisMemberInfoEntity disMemberInfoEntity = disMemberInfoService.queryByMobile(mobile);
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
        String orderNo = AliPayUtil.generateOrderId(mobile, "1");
        //构造支付请求参数
        AliPayParams payParams = new AliPayParams();
        payParams.setSubject("会员升级服务");
        payParams.setOutTradeNo(orderNo);
        payParams.setTotalAmount(amount);
        payParams.setQuitUrl(returnUrl);
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
                //调用分润
                Double money = 0.00;
                switch (level) {
                    case "1":
                        money = 300.00;
                        break;
                    case "2":
                        money = 200.00;
                        break;
                    case "3":
                        money = 100.00;
                        break;
                    default:
                }
                profiParamService.doFeeSplitting(member, money, true);
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
