package com.distribution.wxpay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "pay.wxpay")
@Component
public class WxConfig {
    /**
     * 微信应用id
     *
     * @return
     */
    private String appId;

    /**
     * 商户id
     *
     * @return
     */
    private String mchId;
    /**
     * 安全密钥
     *
     * @return
     */
    private String key;
    /**
     * 服务器通知的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
     * 必须保证其地址能够在互联网中访问的到
     */
    private String notifyUrl;

}
