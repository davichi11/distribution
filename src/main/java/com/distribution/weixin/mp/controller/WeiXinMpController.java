package com.distribution.weixin.mp.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class WeiXinMpController {
    @Autowired
    private WxMpService wxMpService;

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
}
