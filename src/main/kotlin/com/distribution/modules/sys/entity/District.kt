package com.distribution.modules.sys.entity

import java.io.Serializable


/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.sys.entity
 
 * @create 2018/6/20-21:59
 */

data class District(
        private val serialVersionUID: Long = 1L,
        var id: Int? = null,
        var name: String? = null,
        var parent: District? = null,
        var code: String? = null,
        var order: Int? = null
) : Serializable
