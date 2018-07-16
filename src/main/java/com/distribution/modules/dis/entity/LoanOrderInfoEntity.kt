package com.distribution.modules.dis.entity

import com.distribution.common.utils.DateUtils
import java.io.Serializable
import java.time.LocalDateTime


/**
 * 贷款订单
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */

data class LoanOrderInfoEntity(
        /**
         *
         */
        var id: String = "",

        /**
         * 订单编号
         */
        var orderId: String = "",

        /**
         *
         */
        var orderMobile: String = "",

        /**
         *
         */
        var orderIdcardno: String = "",

        /**
         * 订单状态 0失败 1成功 2申请中
         */
        var orderStatus: Int = 0,

        /**
         *
         */
        var memberId: String = "",

        /**
         *
         */
        var loanId: String = "",

        /**
         *
         */
        var loanAmount: Double = 0.00,

        /**
         *
         */
        var isDelete: Int = 1,

        /**
         *
         */
        var addTime: String = DateUtils.formatDateTime(LocalDateTime.now()),

        /**
         *
         */
        var updateTime: String = DateUtils.formatDateTime(LocalDateTime.now()),


        var loanMoney: Double = 0.00
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
