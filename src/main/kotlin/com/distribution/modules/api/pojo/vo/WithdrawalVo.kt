package com.distribution.modules.api.pojo.vo

import java.math.BigDecimal
import javax.validation.constraints.NotNull

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 
 * @create 2018/6/1-00:13
 */

data class WithdrawalVo(
        /**
         * 提现订单号
         */
        var withdrawNum: String = "",
        /**
         * 提现状态 0:失败,1:成功
         */
        var withdrawType: String = "",
        /**
         * 提现人姓名
         */
        var withdrawName: String = "",
        /**
         * 手机号
         */
        @NotNull
        var withdrawMobile: String = "",
        var withdrawCard: String = "",
        /**
         * 金额
         */
        @NotNull
        var withdrawAmount: BigDecimal = BigDecimal.ZERO,
        /**
         * 手续费
         */
        var withdrawPoundage: BigDecimal = BigDecimal(0),
        /**
         * 实际到账金额
         */
        var realAmount: BigDecimal = BigDecimal.ZERO,
        /**
         * 支付宝账户
         */
        var aliPayAccount: String = ""

)
