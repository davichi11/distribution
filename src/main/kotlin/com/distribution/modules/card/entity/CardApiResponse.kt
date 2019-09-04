package com.distribution.modules.card.entity

import java.io.Serializable

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.card.entity
 
 * @create 2018/6/8-20:45
 */

data class CardApiResponse(
        private val serialVersionUID: Long = 1L,
        var message: String = "",

        var results: CardApiResults = CardApiResults(),

        var status: String = "",


        val isSuccess: Boolean = false
) : Serializable
