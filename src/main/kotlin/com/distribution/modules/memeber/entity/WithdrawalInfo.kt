package com.distribution.modules.memeber.entity

import java.math.BigDecimal
import javax.validation.constraints.NotNull

/**
 * 提现信息表
 *
 * @Auther: liuxinxin
 * @Date: 2018/5/22 22:01
 * @Description:
 */

data class WithdrawalInfo(
        var id: String? = null,

        var withdrawNum: String? = null,
        /**
         * 提现状态 0:失败,1:成功
         */
        var withdrawType: String? = null,
        /**
         * 提现金额
         */
        @NotNull
        var withdrawAmount: BigDecimal? = null,
        var withdrawName: String? = null,
        @NotNull
        var withdrawMobile: String? = null,
        var withdrawCard: String? = null,
        var withdrawPoundage: BigDecimal? = null,
        var realAmount: BigDecimal? = null,
        var aliPayAccount: String? = null,
        var isDelete: String? = null,
        var addTime: String? = null,
        var updateTime: String? = null
)
