package com.distribution.modules.sys.entity


/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.sys.entity
 * @Description TODO(描述)
 * @create 2018/6/20-21:59
 */

data class District (
        var id: Int? = null,
        var name: String? = null,
        var parent: District? = null,
        var code: String? = null,
        var order: Int? = null
)
