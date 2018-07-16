package com.distribution.modules.integral.entity

import java.io.Serializable


/**
 * 积分兑换产品类型表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */

data class ProductTypeEntity(
        /**
         *
         */
        var id: String = "",

        /**
         * 产品类型名称
         */
        var prodName: String = "",

        /**
         * 结算周期
         */
        var prodRate: String = "",

        /**
         * 产品描述
         */
        var prodRemark: String = "",


        /**
         * 图标
         */
        var prodImg: String = "",


        var prodType: String = "",

        var exchangePercent: Double = 0.00
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
