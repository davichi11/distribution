
package com.distribution.wxpay.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * description: 微信支付回调
 *
 * @author
 * @see
 * @since
 */
@Data
public class WxPayResult implements Serializable {

    private String appid;
    private String bankType;
    private String cashFee;
    private String feeType;
    private String isSubscribe;
    private String mchId;
    private String nonceStr;
    private String openid;
    private String outTradeNo;
    private String resultCode;
    private String returnCode;
    private String sign;
    private String timeEnd;
    private String totalFee;
    private String tradeType;
    private String transactionId;


    /**
     *
     */
    private static final long serialVersionUID = -1227026039888867970L;

}