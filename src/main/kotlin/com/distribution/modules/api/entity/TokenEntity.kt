package com.distribution.modules.api.entity

import java.io.Serializable
import java.time.LocalDateTime


/**
 * 用户Token
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:07
 */

data class TokenEntity(
        /**
         * 用户ID
         */
        var userId: Long = 0,

        /**
         * token
         */
        var token: String = "",

        /**
         * 过期时间
         */
        var expireTime: LocalDateTime = LocalDateTime.now(),

        /**
         * 更新时间
         */
        var updateTime: LocalDateTime = LocalDateTime.now()
                
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }

}
