package com.distribution.modules.api.entity

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.entity
 * @Description TODO(描述)
 * @create 2018/6/9-13:13
 */

data class IdCardResponse(
        var reason: String = "",

        var result: IdCardResults = IdCardResults(),

        var errorCode: Int = 0
)
