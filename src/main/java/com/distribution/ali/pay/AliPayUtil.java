package com.distribution.ali.pay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.distribution.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.ali.pay
 * @Description TODO(描述)
 * @create 2018/5/29-20:50
 */
@Slf4j
public final class AliPayUtil {
    private AliPayUtil() {
    }

    private static final String APP_ID = "2018052860259186";
    private static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCxYcqg1UQ5eCgN1qFRHWbmdy8RDmMR2+5pgSHh0wo2jx23r4TQi7hA0Vw1R0eyjTDfBR7wB0hIB67yIxL5fcgR+hi/WZuprpMcQsCeVIu9Cn1grCopNtLrW3Lay+cVNeKTOtP3CxzwWcxYe/vC3MY2E/UoiE449oleOkeonzHUJjtP7II9XbYFfSg6TJaCjDdBYn13QBoJIIm7UFQaHKn19SkNHnz8cHnAGLSqqsAfejF0kFn82ucm0mP5ZMjeWc+TLzDjjW5j8hpJlqzPoUeesSDn7YnrkF0zTigUWPDw/YPNeq2W7l/84mXy19Bzz+HJkc50UBJA/Mq7qvmoPg6/AgMBAAECggEAeaidxkOfgF8V8a/UgoLM8aKq6GLatgQG99u2dmE9bwAnnLVIPZSmseYl/PC/jwnhjATCHFSSUbzbU32U5QNM9m93C76U0gNHKKnr3GwWT3jjVyud2xib/wqAsuoIuvZzSRGfEaBQn9SnH8UhfpvXYn27tqoOJGmK+9KzFUbicSMDR+ulBHPJlo8ZOWbIpj0sMUtaBctB5DPZPfA51//PH9LEhX/nQC7e3xebZaJ4DsLT6wfMXvcMlbDUDxzRa9GDPtS3aaKej6XMKQG49KLrPkCHMfhkaQ56sfivzEYucz597NRHf9QPjQXvF/zaQn+okLLlCFc0+oQ21wUASfWXaQKBgQD7vcmCgZ+uk3xt7o3cjAVvIvjrhHyZI412MH94ObKhUQVkqif49A/vqpOowyE0urBRI643Gk/Z5zJoeMdaRWuIFmBcpdU+2wB1aI8ta8FFhfvkpfYhxv4QBirY/o+BsmwpqEEZWbHyLOoqphUy6BLpMuFE9I68kFzn42iJHb8uBQKBgQC0YfoxMmZzmRGpRQe5LUnJrq8Izy12IroWP64iuD81s8YJDzu4psllY6DtmR/i4Oneah2GaB6O2m7z4G2t4WMnLQcb67Hohhx9wMzNNwfWHy3drBn8nLoObxCm/L0lQ+HCDhyvSSiuWIkZBR/em4JQeLCJ5APB+KzdIBQm3E3g8wKBgEtMmoDZ6vyaCwcK6w4EJ//1MwWnjGl2E/M9UHOStpbAGF/i8a/quPWUTTSWoVEXYnPyu0KqMSZZnF9ZeS/MQfzjzJEJ3X4+naWA0ArjB12XVSAwcC3sSsRaQ+jCRASkbOwM8uP1S1/IBYcaRI8EkduFJ5KroCJRACkwgW06I5xNAoGBAIps6egZKmApplEE5XFo3MhGIpKbEuMmsTFjuwlv5SocBKxtIUKJ0+HXu+a57KtaOH7zZib//ftYi27h2qlmXoXw2xTFIikzyeeDLmeJhJ0AHl2Ptf2SdIGhH+FV8P0JA9W8qKyncu2gic1GjndxVWbVx3x6G5webuHevTg5NhabAoGADQiL+nk7RUDOIV0aMaRXd+mwKVIfHuuqXZNVCcxIlEVxRmuTZSoKeHYqzE+HheKVWN+QTndWPe47uSERfy+hkoR2F8VzEvDFwZvdXnqdubUXc5fxPgoCNDHZU8wkrt85uq2uIRd8zegnrfJ3HKsbMQlOCUO9X9l5AqPfo7YE0AA=";
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApmhN5WWx9dlTcf9lqjiNjbqpHu2LxPsLb3OgqmhHdyYIWDRPT6QvXJflkApA3XgsEwm+pm/IqsxSBujT8MlaFsxt28pdED972Ufmm3y+TZgjJpnk4BOdfaJnzCmDXq6hbxncMdRh5UgyWzel+nkOz19P7uVu6MIuxm5+l1wRItrHHfw26ZheF/ZDuh6iQ66/d1m0Qz9IWNImf7Lx06wfLTcdE64ipkaLfhPmb0CGsWt0BaTyZsn3ZWevz1HDUQM9N6bulB8jBGEtNmWnw1KTWY+kOg2FLjrYVOgg1aY3JwyRSGdT98STxTqn4QNZekkJztfdp7c+qfF/ekur7w/EiQIDAQAB";
    private static final String ENCRYPT_KEY = "5KBDk9lXy8eU23DvUNOdgg==";
    private static final String ENCRYPT_TYPE = "AES";


    /**
     * 转账到支付宝账户
     *
     * @param payParams
     * @return
     */
    public static AlipayFundTransToaccountTransferResponse transferResponse(AliPayParams payParams) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, PRIVATE_KEY,
                "json", "GBK", ALIPAY_PUBLIC_KEY, "RSA2", ENCRYPT_KEY, ENCRYPT_TYPE);
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setNeedEncrypt(true);
        request.setBizContent(JSON.toJSONString(payParams));
        return alipayClient.execute(request);

    }

    /**
     * 生成订单号,规则:
     * 支付平台1位 0:支付宝,1:微信 (暂时使用支付宝)
     * 业务类型一位 0:提现,1:充值
     * 时间戳14位
     * 用户手机号后四位
     *
     * @param mobile
     * @param payType 业务类型 0:提现,1:充值
     * @return
     */
    public static String generateOrderId(String mobile, String payType) {
        String payPlatform = "0";
        String time = DateUtils.formatDateTime(LocalDateTime.now(), "yyyyMMddHHmmss");
        String last = StringUtils.substring(mobile, 7, 11);
        return payPlatform + payType + time + last;
    }

    /**
     * 支付宝APP支付异步回调验签
     *
     * @param params
     * @return
     */
    public static boolean signVerified(Map<String, String> params) throws AlipayApiException {
        return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "UTF-8", "RSA2");
    }

    /**
     * 生成APP支付订单
     *
     * @param remark  产品描述
     * @param title   订单标题
     * @param amount  金额
     * @param orderNo 订单号
     * @return
     */
    public static String generateAppOrder(String remark, String title, BigDecimal amount, String orderNo) throws AlipayApiException {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                APP_ID, PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(remark);
        model.setSubject(title);
        model.setOutTradeNo(orderNo);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(amount.toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("商户外网可以访问的异步地址");
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        System.out.println(response.getBody());
        return response.getBody();
    }

    /**
     * 手机网站支付
     *
     * @param payParams 请求参数
     * @return
     */
    public static AlipayTradeWapPayResponse tradeWapPay(AliPayParams payParams) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                APP_ID, PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2", ENCRYPT_KEY, ENCRYPT_TYPE);
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNeedEncrypt(true);
        request.setBizContent(JSON.toJSONString(payParams));
        return alipayClient.execute(request);
    }
}
