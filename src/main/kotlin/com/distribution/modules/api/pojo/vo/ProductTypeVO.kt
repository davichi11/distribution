package com.distribution.modules.api.pojo.vo

import com.distribution.pojo.tables.pojos.IntegralTutorial

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 * @Description TODO(描述)
 * @create 2018/7/8-15:17
 */

data class ProductTypeVO (
        var id: String = "",
        var prodName: String = "",
        var prodRate: String = "",
        var prodRemark: String = "",
        var prodImg: String = "",
        var prodType: String = "",
        var exchangePercent: Double = 0.00,
        var tutorials: List<IntegralTutorial> = mutableListOf()
)
