package com.distribution.ali.pay

import com.alibaba.fastjson.JSON
import com.alipay.api.AlipayApiException
import com.alipay.api.DefaultAlipayClient
import com.alipay.api.domain.AlipayTradeAppPayModel
import com.alipay.api.domain.AlipayTradeWapPayModel
import com.alipay.api.internal.util.AlipaySignature
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest
import com.alipay.api.request.AlipayTradeAppPayRequest
import com.alipay.api.request.AlipayTradeWapPayRequest
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse
import com.alipay.api.response.AlipayTradeWapPayResponse
import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.DateUtils
import org.apache.commons.lang.StringUtils
import org.slf4j.LoggerFactory
import java.io.IOException
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.ali.pay
 * @Description TODO(描述)
 * @create 2018/5/29-20:50
 */
object AliPayUtils {

    private const val APP_ID = "2018052860259186"
    private const val PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCxYcqg1UQ5eCgN1qFRHWbmdy8RDmMR2+5pgSHh0wo2jx23r4TQi7hA0Vw1R0eyjTDfBR7wB0hIB67yIxL5fcgR+hi/WZuprpMcQsCeVIu9Cn1grCopNtLrW3Lay+cVNeKTOtP3CxzwWcxYe/vC3MY2E/UoiE449oleOkeonzHUJjtP7II9XbYFfSg6TJaCjDdBYn13QBoJIIm7UFQaHKn19SkNHnz8cHnAGLSqqsAfejF0kFn82ucm0mP5ZMjeWc+TLzDjjW5j8hpJlqzPoUeesSDn7YnrkF0zTigUWPDw/YPNeq2W7l/84mXy19Bzz+HJkc50UBJA/Mq7qvmoPg6/AgMBAAECggEAeaidxkOfgF8V8a/UgoLM8aKq6GLatgQG99u2dmE9bwAnnLVIPZSmseYl/PC/jwnhjATCHFSSUbzbU32U5QNM9m93C76U0gNHKKnr3GwWT3jjVyud2xib/wqAsuoIuvZzSRGfEaBQn9SnH8UhfpvXYn27tqoOJGmK+9KzFUbicSMDR+ulBHPJlo8ZOWbIpj0sMUtaBctB5DPZPfA51//PH9LEhX/nQC7e3xebZaJ4DsLT6wfMXvcMlbDUDxzRa9GDPtS3aaKej6XMKQG49KLrPkCHMfhkaQ56sfivzEYucz597NRHf9QPjQXvF/zaQn+okLLlCFc0+oQ21wUASfWXaQKBgQD7vcmCgZ+uk3xt7o3cjAVvIvjrhHyZI412MH94ObKhUQVkqif49A/vqpOowyE0urBRI643Gk/Z5zJoeMdaRWuIFmBcpdU+2wB1aI8ta8FFhfvkpfYhxv4QBirY/o+BsmwpqEEZWbHyLOoqphUy6BLpMuFE9I68kFzn42iJHb8uBQKBgQC0YfoxMmZzmRGpRQe5LUnJrq8Izy12IroWP64iuD81s8YJDzu4psllY6DtmR/i4Oneah2GaB6O2m7z4G2t4WMnLQcb67Hohhx9wMzNNwfWHy3drBn8nLoObxCm/L0lQ+HCDhyvSSiuWIkZBR/em4JQeLCJ5APB+KzdIBQm3E3g8wKBgEtMmoDZ6vyaCwcK6w4EJ//1MwWnjGl2E/M9UHOStpbAGF/i8a/quPWUTTSWoVEXYnPyu0KqMSZZnF9ZeS/MQfzjzJEJ3X4+naWA0ArjB12XVSAwcC3sSsRaQ+jCRASkbOwM8uP1S1/IBYcaRI8EkduFJ5KroCJRACkwgW06I5xNAoGBAIps6egZKmApplEE5XFo3MhGIpKbEuMmsTFjuwlv5SocBKxtIUKJ0+HXu+a57KtaOH7zZib//ftYi27h2qlmXoXw2xTFIikzyeeDLmeJhJ0AHl2Ptf2SdIGhH+FV8P0JA9W8qKyncu2gic1GjndxVWbVx3x6G5webuHevTg5NhabAoGADQiL+nk7RUDOIV0aMaRXd+mwKVIfHuuqXZNVCcxIlEVxRmuTZSoKeHYqzE+HheKVWN+QTndWPe47uSERfy+hkoR2F8VzEvDFwZvdXnqdubUXc5fxPgoCNDHZU8wkrt85uq2uIRd8zegnrfJ3HKsbMQlOCUO9X9l5AqPfo7YE0AA="
    private const val ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApmhN5WWx9dlTcf9lqjiNjbqpHu2LxPsLb3OgqmhHdyYIWDRPT6QvXJflkApA3XgsEwm+pm/IqsxSBujT8MlaFsxt28pdED972Ufmm3y+TZgjJpnk4BOdfaJnzCmDXq6hbxncMdRh5UgyWzel+nkOz19P7uVu6MIuxm5+l1wRItrHHfw26ZheF/ZDuh6iQ66/d1m0Qz9IWNImf7Lx06wfLTcdE64ipkaLfhPmb0CGsWt0BaTyZsn3ZWevz1HDUQM9N6bulB8jBGEtNmWnw1KTWY+kOg2FLjrYVOgg1aY3JwyRSGdT98STxTqn4QNZekkJztfdp7c+qfF/ekur7w/EiQIDAQAB"
    private const val ENCRYPT_KEY = "5KBDk9lXy8eU23DvUNOdgg=="
    private const val ENCRYPT_TYPE = "AES"
    private val log = LoggerFactory.getLogger(AliPayUtils::class.java)

    /**
     * 转账到支付宝账户
     *
     * @param payParams
     * @return
     */
    @Throws(AlipayApiException::class)
    fun transferResponse(payParams: AliPayParams): AlipayFundTransToaccountTransferResponse {
        val alipayClient = DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, PRIVATE_KEY,
                "json", "GBK", ALIPAY_PUBLIC_KEY, "RSA2", ENCRYPT_KEY, ENCRYPT_TYPE)
        val request = AlipayFundTransToaccountTransferRequest()
        request.isNeedEncrypt = true
        request.bizContent = JSON.toJSONString(payParams)
        return alipayClient.execute(request)

    }

    /**
     * 生成订单号,规则:
     * 支付平台1位 0:支付宝,1:微信 (暂时使用支付宝)
     * 业务类型一位 0:提现,1:充值,2:申请信用卡
     * 时间戳14位
     * 用户手机号后四位+4位随机数
     *
     * @param mobile
     * @param payType 业务类型 0:提现,1:充值,2申请信用卡
     * @return
     */
    fun generateOrderId(mobile: String, payType: String): String {
        val payPlatform = "0"
        val time = DateUtils.formatDateTime(LocalDateTime.now(), "yyyyMMddHHmmss")
        val last = StringUtils.substring(mobile, 7, 11)
        return payPlatform + payType + time + last + CommonUtils.random
    }

    /**
     * 支付宝APP支付异步回调验签
     *
     * @param params
     * @return
     */
    @Throws(AlipayApiException::class)
    fun signVerified(params: Map<String, String>): Boolean {
        return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "UTF-8", "RSA2")
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
    @Throws(AlipayApiException::class)
    fun generateAppOrder(remark: String, title: String, amount: BigDecimal, orderNo: String): String {
        //实例化客户端
        val alipayClient = DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                APP_ID, PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2")
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        val request = AlipayTradeAppPayRequest()
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        val model = AlipayTradeAppPayModel()
        model.body = remark
        model.subject = title
        model.outTradeNo = orderNo
        model.timeoutExpress = "30m"
        model.totalAmount = amount.toString()
        model.productCode = "QUICK_MSECURITY_PAY"
        request.bizModel = model
        request.notifyUrl = "商户外网可以访问的异步地址"
        //这里和普通的接口调用不同，使用的是sdkExecute
        val response = alipayClient.sdkExecute(request)
        println(response.body)
        return response.body
    }

    /**
     * 手机网站支付
     *
     * @param payParams 请求参数
     * @return
     */
    @Throws(AlipayApiException::class, IOException::class)
    fun tradeWapPay(payParams: AliPayParams): String {
        //        AliPayApiConfig aliPayApiConfig = AliPayApiConfig.New()
        //                .setAppId(APP_ID)
        //                .setAlipayPublicKey(ALIPAY_PUBLIC_KEY)
        //                .setCharset("UTF-8")
        //                .setPrivateKey(PRIVATE_KEY)
        //                .setServiceUrl("https://openapi.alipay.com/gateway.do")
        //                .setSignType("RSA2")
        //                .build();
        //        AliPayApiConfigKit.setThreadLocalAliPayApiConfig(aliPayApiConfig);

        val alipayClient = DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                APP_ID, PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2")
        val payModel = AlipayTradeWapPayModel()
        payModel.quitUrl = payParams.quitUrl
        payModel.outTradeNo = payParams.outTradeNo
        payModel.body = payParams.subject
        payModel.totalAmount = payParams.totalAmount!!.toString()
        //        payModel.setTotalAmount("0.01");
        payModel.passbackParams = payParams.passbackParams
        payModel.subject = payParams.subject
        payModel.productCode = payParams.productCode
        payModel.timeoutExpress = "2m"
        val request = AlipayTradeWapPayRequest()
        //        request.setNeedEncrypt(true);
        //        request.setBizContent(JSON.toJSONString(payParams));
        request.bizModel = payModel
        request.notifyUrl = "https://www.qiandaoshou.cn/dis/api/alipayCallback"
        request.returnUrl = "https://www.qiandaoshou.cn/#/MyCenter"
        log.info("请求参数为{}", JSON.toJSON(request))
        //        FileUtils.writeByteArrayToFile(new File("/Users/huchunliang/Documents/form.txt"), alipayClient.pageExecute(request).getBody().getBytes());
        return alipayClient.pageExecute<AlipayTradeWapPayResponse>(request).body
    }
}
