package com.distribution.modules.api.entity

import java.io.Serializable

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.entity
 
 * @create 2018/6/9-13:13
 */

data class IdCardResponse(
        var reason: String = "",

        var result: IdCardResults = IdCardResults(),

        var errorCode: Int = 0,
        private val serialVersionUID: Long = 1L
) : Serializable
