package com.distribution.modules.integral.entity

import java.io.Serializable


/**
 * 积分兑换产品列表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */

data class ProductDetailEntity(
        /**
         *
         */
        var id: String = "",

        /**
         * 产品名称
         */
        var prodDetailName: String = "",

        var prodDetailUrl: String = "",

        /**
         * 回购价
         */
        var prodDetailBuyBack: Int = 0,

        /**
         * 产品类型关联ID
         */
        var prodTypeId: String = "",


        var typeName: String = "",

        /**
         * 产品积分数
         */
        var prodDetailValue: Long = 0L,

        /**
         * 兑换次数
         */
        var prodDetailCount: String = "",
        var isDelete: String = "1"
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
