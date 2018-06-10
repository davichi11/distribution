package com.distribution.weixin.mp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.service.UserService;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.DisFansService;
import com.distribution.modules.dis.service.DisMemberInfoService;
import com.distribution.queue.LevelUpSender;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpMenuServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.weixin.mp.controller
 * @Description TODO(描述)
 * @create 2018/5/27-15:54
 */
@Api("微信接口")
@Slf4j
@Controller
@RequestMapping("/api")
public class WeiXinMpController {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private DisFansService disFansService;
    @Autowired
    private DisMemberInfoService disMemberInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private LevelUpSender levelUpSender;
    @Value("${risk.url}")
    private String url;
    @Value("${risk.return-url}")
    private String returnUrl;

    @AuthIgnore
    @GetMapping("/weixin")
    @ResponseBody
    public String check(String signature, String timestamp, String nonce, String echostr) {
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            return "非法请求";
        }

        return echostr;
    }

    /**
     * 生成用户授权链接,用于获取授权code
     *
     * @param mobile 推荐人ID
     * @return
     */
    @ApiOperation(value = "生成用户授权链接,用于获取授权code")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "会员手机号")
    @AuthIgnore
    @GetMapping("/weixin/oauthUrl")
    @ResponseBody
    public String buildOauthUrl(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            mobile = userService.queryObject("1").getMobile();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("to", "/");
        return wxMpService.oauth2buildAuthorizationUrl(returnUrl, "snsapi_userinfo", JSON.toJSONString(map));
    }


    @ApiOperation(value = "生成用户分享链接")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "会员手机号"),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "to", value = "跳转链接")
    })
    @AuthIgnore
    @GetMapping("/weixin/shareUrl")
    public String buildOauthUrl(String mobile, String to) {
        if (StringUtils.isBlank(mobile)) {
            mobile = userService.queryObject("1").getMobile();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("to", to);
        String url = wxMpService.oauth2buildAuthorizationUrl(returnUrl, "snsapi_userinfo", JSON.toJSONString(map));
        log.info("URL={}", url);
        return "redirect:" + url;
    }


    @AuthIgnore
    @GetMapping("/createMenu")
    @ResponseBody
    public String menu() {
        WxMpMenuService menuService = new WxMpMenuServiceImpl(wxMpService);
        WxMenuButton button = new WxMenuButton();
        button.setName("首页");
        button.setType("view");
        button.setUrl("http://www.qiandaoshou.cn/wx/#/auth");
        WxMenu menu = new WxMenu();
        menu.setButtons(Lists.newArrayList(button));
        String create = "";
        try {
            create = menuService.menuCreate(menu);
            log.info(create);
        } catch (WxErrorException e) {
            log.error("创建菜单异常", e);
            return "";
        }
        return create;
    }

    /**
     * 获取用户信息(scope为 snsapi_userinfo)
     * 锁粉
     *
     * @param code
     * @param state 推荐人ID
     * @return
     */
    @AuthIgnore
    @ApiOperation(value = "微信授权回调地址", notes = "接收微信回调获取用户信息+锁粉")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "微信code"),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "state", value = "会员手机号")
    })
    @GetMapping("/getUserInfo")
    public String getUserInfo(String code, String state) {
        log.info(state);
        JSONObject jsonObject = JSON.parseObject(state);
        String mobile = jsonObject.getString("mobile");
        String to = jsonObject.getString("to");
        String returnUrl = "";
        if (StringUtils.isBlank(mobile)) {
            mobile = userService.queryObject("1").getMobile();
        }
        WxMpOAuth2AccessToken accessToken;
        WxMpUser user;
        try {
            //通过code换取网页授权access_token
            accessToken = wxMpService.oauth2getAccessToken(code);
            //拉取用户信息
            user = wxMpService.oauth2getUserInfo(accessToken, null);
            log.info("用户信息为{}", user);
        } catch (WxErrorException e) {
            log.error("拉取用户信息异常", e);
            return "redirect:" + url;

        }
        if (StringUtils.isNotBlank(to)) {
            returnUrl = url + "?openId=" + user.getOpenId() + "&to=" + to;
        } else {
            returnUrl = url + "?openId=" + user.getOpenId();
        }
        //判断该用户是否已关注或已注册
        if (disFansService.queryByOpenId(user.getOpenId()) != null ||
                disMemberInfoService.queryByOpenId(user.getOpenId()) != null) {
            return "redirect:" + returnUrl;
        }

        DisFans disFans = new DisFans();
        disFans.setId(CommonUtils.getUUID());
        disFans.setWechatId(user.getOpenId());
        disFans.setWechatImg(user.getHeadImgUrl());
        disFans.setWechatNickname(user.getNickname());

        //查询推荐人是否存在
        DisMemberInfoEntity disMemberInfo = disMemberInfoService.queryByMobile(mobile);
        //如果是自己,直接返回
        if (disMemberInfo.getOpenId().equals(user.getOpenId())) {
            return "redirect:" + returnUrl;
        }
        //关联推荐人
        disFans.setDisMemberInfo(disMemberInfo);
        //发送新会员加入模板信息
        WxMpTemplateMessage templateMessage = buildTemplateMsg(disMemberInfo.getOpenId(), disFans, disMemberInfo.getDisUserName());
        try {
            disFansService.save(disFans);
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            //异步执行会员升级逻辑
            levelUpSender.send(JSON.toJSONString(disMemberInfo));
        } catch (Exception e) {
            log.error("保存锁粉信息异常", e);
        }
        return "redirect:" + returnUrl;
    }

    /**
     * 构造新会员加入模板信息
     *
     * @param openId
     * @param disFans
     * @param name
     * @return
     */
    private WxMpTemplateMessage buildTemplateMsg(String openId, DisFans disFans, String name) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("-IxOfOp7QmIBKcS2MZ8c9WT35bBT6HO4yKfcj3C3u0E");
        wxMpTemplateMessage.setToUser(openId);
        List<WxMpTemplateData> templateDataList = Lists.newArrayList(
                new WxMpTemplateData("first", MessageFormat.format("尊敬的会员：{0}，恭喜您，有新会员加入！", name)),
                new WxMpTemplateData("keyword1", disFans.getId()),
                new WxMpTemplateData("keyword2", DateUtils.formatDateTime(LocalDateTime.now())),
                new WxMpTemplateData("remark", "感谢您的努力")
        );
        wxMpTemplateMessage.setData(templateDataList);
        return wxMpTemplateMessage;
    }


    /**
     * 生成jssdk对应配置
     *
     * @return
     */
    @ApiOperation(value = "生成jssdk对应配置")
    @AuthIgnore
    @GetMapping("/weixin/jsConfig")
    @ResponseBody
    public Result jsConfig(String url) {
        try {
            return Result.ok().put("config", wxMpService.createJsapiSignature(url));
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }
}
