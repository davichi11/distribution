package com.distribution.modules.card.entity

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.card.entity
 * @Description TODO(描述)
 * @create 2018/6/8-20:45
 */

data class CardApiResponse(
        var message: String = "",

        var results: CardApiResults = CardApiResults(),

        var status: String = "",


        val isSuccess: Boolean = false
)
