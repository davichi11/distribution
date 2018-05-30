package com.distribution.ali.pay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.distribution.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

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
    private static final String PRIVATE_KEY = "";
    private static final String ALIPAY_PUBLIC_KEY = "";


    /**
     * 转账到支付宝账户
     *
     * @param payParams
     * @return
     */
    public static AlipayFundTransToaccountTransferResponse transferResponse(AliPayParams payParams) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, PRIVATE_KEY,
                "json", "GBK", ALIPAY_PUBLIC_KEY, "RSA2");
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent(JSON.toJSONString(payParams));
        return alipayClient.execute(request);

    }

    /**
     * 生成订单号,规则:
     * 支付平台1位 0:支付宝,1:微信 (暂时使用支付宝)
     * 业务类型一位 0:提现,1:充值
     * 时间戳14位
     * 用户手机号后四位
     * @param mobile
     * @param payType 业务类型
     * @return
     */
    public static String generateOrderId(String mobile,String payType) {
        String payPlatform = "0";
        String time = DateUtils.formatDateTime(LocalDateTime.now(), "yyyyMMddHHmmss");
        String last = StringUtils.substring(mobile, 7, 11);
        return payPlatform + payType + time + last;
    }

    /**
     * 支付宝APP支付异步回调验签
     * @param params
     * @return
     */
    public static boolean signVerified(Map<String, String> params) throws AlipayApiException {
        return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "UTF-8","RSA2");
    }
}
