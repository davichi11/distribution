package com.distribution.modules.api.pojo.vo

import com.distribution.modules.integral.entity.ProductDetailParams

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 
 * @create 2018/7/8-00:15
 */

data class ProductDetailVO(
        var id: String? = null,
        var prodDetailName: String? = null,
        var prodDetailUrl: String? = null,
        var prodDetailBuyBack: Int? = null,
        var prodTypeId: String? = null,
        var prodDetailValue: Long? = null,
        var prodDetailCount: String? = null,
        var params: List<ProductDetailParams> = mutableListOf()
)
