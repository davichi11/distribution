package com.distribution.modules.api.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 * @Description TODO(描述)
 * @create 2018/6/1-00:13
 */
@Data
public class WithdrawalVo {
    /**
     * 提现订单号
     */
    private String withdrawNum;
    /**
     * 提现状态 0:失败,1:成功
     */
    private String withdrawType;
    /**
     * 提现人姓名
     */
    private String withdrawName;
    /**
     * 手机号
     */
    @NotNull
    private String withdrawMobile;
    private String withdrawCard;
    /**
     * 金额
     */
    @NotNull
    private BigDecimal withdrawAmount;
    /**
     * 手续费
     */
    private BigDecimal withdrawPoundage = new BigDecimal(0);
    /**
     * 实际到账金额
     */
    private BigDecimal realAmount;
    /**
     * 支付宝账户
     */
    private String aliPayAccount;
}
