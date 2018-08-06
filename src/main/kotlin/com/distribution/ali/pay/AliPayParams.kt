package com.distribution.ali.pay

import com.alibaba.fastjson.annotation.JSONField

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.ali.pay
 * @Description TODO(描述)
 * @create 2018/5/29-22:23
 */
data class AliPayParams(
        /**
         * 商户转账唯一订单号
         */
        @JSONField(name = "out_biz_no")
        var outBizNo: String? = null,
        @JSONField(name = "payee_type")
        var payeeType: String = "ALIPAY_LOGONID",
        /**
         * 收款方账户
         */
        @JSONField(name = "payee_account")
        var payeeAccount: String? = null,
        /**
         * 金额
         */
        var amount: Double? = null,
        /**
         * 收款方真实姓名
         */
        @JSONField(name = "payee_real_name")
        var payeeRealName: String? = null,
        /**
         * 转账备注.转账金额达到（大于等于）50000元，remark不能为空。
         * 收款方可见，会展示在收款用户的收支详情中。
         */
        var remark: String? = null,
        /**
         * 商品的标题/交易标题/订单标题/订单关键字等
         */
        var subject: String? = null,
        /**
         * 商户网站唯一订单号
         */
        @JSONField(name = "out_trade_no")
        var outTradeNo: String? = null,
        /**
         * 订单超时时间 默认30M
         */
        @JSONField(name = "timeout_express")
        var timeoutExpress: String = "30m",
        /**
         * 订单总金额，单位为元，精确到小数点后两位
         */
        @JSONField(name = "total_amount")
        var totalAmount: Double? = null,
        /**
         * 用户付款中途退出返回商户网站的地址
         */
        @JSONField(name = "quit_url")
        var quitUrl: String? = null,
        /**
         * 公用回传参数
         */
        @JSONField(name = "passback_params")
        var passbackParams: String? = null,
        /**
         * 销售产品码，商家和支付宝签约的产品码
         */
        @JSONField(name = "product_code")
        var productCode: String = "QUICK_WAP_WAY",
        /**
         * 收款支付宝用户ID
         */
        @JSONField(name = "seller_id")
        var sellerId: String = "2088131379266664"
)
