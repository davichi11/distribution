package com.distribution.weixin.wxpay.demo;

import lombok.Data;

@Data
public class WxPayDto {

    /**
     * 订单号
     */
    private String orderId;
    /**
     * 金额
     */
    private String totalFee;
    /**
     * 订单生成的机器 IP
     */
    private String spbillCreateIp;
    /**
     * 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等
     */
    private String notifyUrl;
    /**
     * 商品描述根据情况修改
     */
    private String body;
    /**
     * 微信用户对一个公众号唯一
     */
    private String openId;


}
