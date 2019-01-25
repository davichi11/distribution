package com.distribution.modules.integral.entity

import java.io.Serializable

/**
 * Created by Mybatis Generator 2018/12/04
 */
data class ProductDetailParams(

        /* */
        var id: Int? = null,

        /* */
        var detailId: String? = null,

        /* */
        var level: Int? = null,

        /* */
        var buyBackPrice: Double? = null

) : Serializable {
    companion object {

        private const val serialVersionUID = 1L
    }
}