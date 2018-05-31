package com.distribution.weixin.mp.controller;

import com.alibaba.fastjson.JSON;
import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.DisFansService;
import com.distribution.modules.dis.service.DisMemberInfoService;
import com.distribution.queue.LevelUpSender;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.weixin.mp.controller
 * @Description TODO(描述)
 * @create 2018/5/27-15:54
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class WeiXinMpController {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private DisFansService disFansService;
    @Autowired
    private DisMemberInfoService disMemberInfoService;
    @Autowired
    private LevelUpSender levelUpSender;
    @Value("{risk.url}")
    private String url;

    @AuthIgnore
    @GetMapping("/weixin")
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
     * @param memberId 推荐人ID
     * @return
     */
    @AuthIgnore
    @GetMapping("/weixin/oauthUrl")
    public String buildOauthUrl(String memberId) {
        return wxMpService.oauth2buildAuthorizationUrl(url, "snsapi_userinfo", memberId);
    }

    /**
     * 获取用户信息(scope为 snsapi_userinfo)
     * 锁粉
     *
     * @param code
     * @param state 推荐人ID
     * @return
     */
    @PostMapping("/weixin/getUserInfo")
    public Result getUserInfo(String code, String state) {
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
            return Result.error("拉取用户信息异常");
        }
        DisFans disFans = new DisFans();
        disFans.setId(CommonUtils.getUUID());
        disFans.setWechatId(user.getOpenId());
        disFans.setWechatImg(user.getHeadImgUrl());
        disFans.setWechatNickname(user.getNickname());
        //查询推荐人是否存在
        DisMemberInfoEntity disMemberInfo = disMemberInfoService.queryObject(state);
        if (disMemberInfo != null) {
            //关联推荐人
            disFans.setDisMemberInfo(disMemberInfo);
            //异步执行会员升级逻辑
            levelUpSender.send(JSON.toJSONString(disMemberInfo));
        }
        try {
            disFansService.save(disFans);
        } catch (Exception e) {
            log.error("保存锁粉信息异常", e);
            return Result.error("保存锁粉信息异常");
        }
        return Result.ok("锁粉成功");

    }
}
