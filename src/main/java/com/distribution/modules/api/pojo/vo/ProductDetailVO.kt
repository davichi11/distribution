package com.distribution.modules.api.pojo.vo

import com.distribution.pojo.tables.pojos.ProductDetailParams

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 * @Description TODO(描述)
 * @create 2018/7/8-00:15
 */

data class ProductDetailVO(
        var id: String = "",
        var prodDetailName: String = "",
        var prodDetailUrl: String = "",
        var prodDetailBuyBack: Int = 0,
        var prodTypeId: String = "",
        var prodDetailValue: Long = 0L,
        var prodDetailCount: String = "",
        internal var params: List<ProductDetailParams> = mutableListOf()
)
