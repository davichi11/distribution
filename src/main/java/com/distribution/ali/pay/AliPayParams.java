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
     *  商户转账唯一订单号
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
}
