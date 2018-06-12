package com.distribution.modules.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.distribution.ali.pay.AliPayUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.account.entity.MemberAccount;
import com.distribution.modules.account.service.MemberAccountHistoryService;
import com.distribution.modules.account.service.MemberAccountService;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.config.JWTConfig;
import com.distribution.modules.api.entity.UserEntity;
import com.distribution.modules.api.pojo.vo.DisMemberVO;
import com.distribution.modules.api.service.UserService;
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
import io.jsonwebtoken.Claims;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
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
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
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
                    DisMemberInfoEntity memberInfo = Optional.ofNullable(disMemberInfoService.queryByOpenId(disFans.getWechatId()))
                            .orElse(new DisMemberInfoEntity());
                    return getDisMemberVO(disFans, memberInfo);
                }).filter(vo -> "0".equals(vo.getDisUserType())).collect(Collectors.toList());

        //所有代理信息
        List<DisMemberVO> children = member.getDisMemberChildren().stream().filter(m -> m != null && "1".equals(m.getDisUserType()))
                .map(memberInfo -> {
                    DisFans disFans = Optional.ofNullable(disFansService.queryByOpenId(memberInfo.getOpenId())).orElse(new DisFans());
                    memberInfo.setUserEntity(userService.queryByMemberId(memberInfo.getId()));
                    return getDisMemberVO(disFans, memberInfo);
                }).collect(Collectors.toList());

        //返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("countFans", Optional.ofNullable(disFansList).orElse(new ArrayList<>()).size());
        map.put("fansList", disFansList);
        map.put("countChirldern", Optional.ofNullable(children).orElse(new ArrayList<>()).size());
        map.put("children", children);
        return Result.ok().put("results", map);
    }

    @NotNull
    private DisMemberVO getDisMemberVO(DisFans disFans, DisMemberInfoEntity memberInfo) {
        DisMemberVO memberVO = new DisMemberVO();
        memberVO.setDisUserName(memberInfo.getDisUserName());
        memberVO.setDisUserType(memberInfo.getDisUserType());
        memberVO.setDisLevel(memberInfo.getDisLevel());
        memberVO.setMobile(Optional.ofNullable(memberInfo.getUserEntity()).orElse(new UserEntity()).getMobile());
        memberVO.setOpenId(memberInfo.getOpenId());
        memberVO.setAddTime(StringUtils.substring(memberInfo.getAddTime(), 0, 10));
        memberVO.setNickName(disFans.getWechatNickname());
        memberVO.setImgUrl(disFans.getWechatImg());
        return memberVO;
    }


    @GetMapping("/memberAccount/{mobile}")
    @ApiOperation(value = "查询用户账户信息")
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

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("/memberByToken")
    public Result getMemberInfoByToken(String token, HttpServletRequest request) {
        if (StringUtils.isBlank(token)) {
            if (StringUtils.isNotBlank(request.getHeader("token"))) {
                token = request.getHeader("token");
            } else {
                return Result.error("token不能为空");
            }
        }
        Claims claims = jwtConfig.getClaimByToken(token);
        String userId = claims.getSubject();
        UserEntity userEntity = userService.queryObject(userId);
        if (userEntity == null) {
            return Result.error("请先注册");
        }
        DisMemberInfoEntity memberInfoEntity = disMemberInfoService.queryByMobile(userEntity.getMobile());
        DisMemberVO memberVO = buildMemberVO(userEntity, memberInfoEntity);
        return Result.ok().put("memberInfo", memberVO);
    }

    private DisMemberVO buildMemberVO(UserEntity userEntity, DisMemberInfoEntity memberInfo) {
        DisMemberVO disMemberVO = new DisMemberVO();
        DisFans fans = disFansService.queryByOpenId(memberInfo.getOpenId());
        disMemberVO.setDisUserName(memberInfo.getDisUserName());
        disMemberVO.setDisUserType(memberInfo.getDisUserType());
        disMemberVO.setDisLevel(memberInfo.getDisLevel());
        disMemberVO.setMobile(userEntity.getMobile());
        disMemberVO.setIdCode(memberInfo.getIdCode());
        disMemberVO.setOpenId(memberInfo.getOpenId());
        disMemberVO.setAddTime(memberInfo.getAddTime());
        if (fans != null) {
            disMemberVO.setNickName(fans.getWechatNickname());
            disMemberVO.setImgUrl(fans.getWechatImg());
        }
        return disMemberVO;
    }


    @AuthIgnore
    @GetMapping("/upLevel/{openId}")
    @ApiOperation(value = "查询用户上级信息")
    public Result getUpLevel(@PathVariable("openId") String openId) {
        DisFans fans = disFansService.queryByOpenId(openId);
        //获取上级会员信息
        DisMemberInfoEntity parent = fans.getDisMemberInfo();
        if (parent == null) {
            return Result.error("用户不存在");
        }
        DisFans disFans = disFansService.queryByOpenId(parent.getOpenId());
        DisMemberVO memberVO = new DisMemberVO();
        memberVO.setDisUserName(parent.getDisUserName());
        memberVO.setDisUserType(parent.getDisLevel().toString());
        memberVO.setMobile(userService.queryByMemberId(parent.getId()).getMobile());
        memberVO.setOpenId(parent.getOpenId());
        memberVO.setNickName(disFans.getWechatNickname());
        memberVO.setImgUrl(disFans.getWechatImg());
        memberVO.setDisLevel(parent.getDisLevel());

        return Result.ok().put("upLevel", memberVO);
    }


    @AuthIgnore
    @ApiOperation(value = "查询微信关注者信息")
    @GetMapping("/disFans")
    public Result disFansInfo(@RequestParam String openId) {
        DisFans fans = disFansService.queryByOpenId(openId);
        DisMemberVO memberVO = new DisMemberVO();
        memberVO.setMobile("");
        memberVO.setToken("");
        memberVO.setExpire(null);
        DisMemberInfoEntity member = disMemberInfoService.queryByOpenId(openId);
        if (member != null) {
            BeanUtils.copyProperties(member, memberVO);
            memberVO.setToken(jwtConfig.generateToken(member.getUserEntity().getUserId()));
            memberVO.setExpire(jwtConfig.getExpire());
            memberVO.setMobile(member.getUserEntity().getMobile());
        }
        memberVO.setImgUrl(fans.getWechatImg());
        memberVO.setNickName(fans.getWechatNickname());
        memberVO.setOpenId(openId);
        return Result.ok().put("weixinInfo", memberVO);
    }

    @ApiOperation("更新会员手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "oldMobile", value = "旧手机号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "captcha", value = "验证码", required = true)
    })
    @PostMapping("/updateMobile")
    public Result updateMobile(String mobile, String oldMobile, String captcha) {
        if (!redisTemplate.opsForValue().get(mobile).equals(captcha)) {
            return Result.error("验证码不正确");
        }
        UserEntity userEntity = userService.queryByMobile(oldMobile);
        if (userEntity == null) {
            return Result.error("用户不存在");
        }
        userEntity.setMobile(mobile);
        try {
            userService.update(userEntity);
        } catch (Exception e) {
            log.error("更新用户手机号异常", e);
            return Result.error("更新用户手机号异常");
        }
        return Result.ok("更新用户手机号成功");
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
            if (AliPayUtils.signVerified(params)) {
                //从回传参数中取出购买的会员等级
                String level = URLDecoder.decode(params.get("passbackParams"), "UTF-8");
                if (StringUtils.isBlank(level)) {
                    log.info("回传参数中没有会员等级");
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
