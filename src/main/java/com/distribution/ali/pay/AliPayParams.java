package com.distribution.ali.pay;

import lombok.Data;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.ali.pay
 * @Description TODO(描述)
 * @create 2018/5/29-22:23
 */
@Data
public class AliPayParams {
    /**
     * 商户转账唯一订单号
     */
    String outBizNo;
    String payeeType = "ALIPAY_LOGONID";
    /**
     * 收款方账户
     */
    String payeeAccount;
    /**
     * 金额
     */
    String amount;
    /**
     * 收款方真实姓名
     */
    String payeeRealName;
    /**
     * 转账备注.转账金额达到（大于等于）50000元，remark不能为空。
     * 收款方可见，会展示在收款用户的收支详情中。
     */
    String remark;
    /**
     * 商品的标题/交易标题/订单标题/订单关键字等
     */
    String subject;
    /**
     * 商户网站唯一订单号
     */
    String outTradeNo;
    /**
     * 订单超时时间 默认30M
     */
    String timeoutExpress = "30m";
    /**
     * 订单总金额，单位为元，精确到小数点后两位
     */
    String totalAmount;
    /**
     * 用户付款中途退出返回商户网站的地址
     */
    String quitUrl;
    /**
     * 公用回传参数
     */
    String passbackParams;
    /**
     * 销售产品码，商家和支付宝签约的产品码
     */
    String productCode = "QUICK_WAP_WAY";
    /**
     * 收款支付宝用户ID
     */
    String sellerId = "2088131379266664";
}
