package com.distribution.modules.api.entity

import com.distribution.modules.dis.entity.DisMemberInfoEntity
import java.io.Serializable
import java.time.LocalDateTime


/**
 * 用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:06
 */

data class UserEntity(
        /**
         * 用户ID uuid
         */
        var userId: String? = null,

        /**
         * 用户名
         */
        var username: String? = null,

        /**
         * 手机号
         */
        var mobile: String? = null,

        /**
         * 密码
         */
        @Transient
        var password: String? = null,
        /**
         * 创建时间
         */
        var createTime: LocalDateTime? = null,

        var memberInfo: DisMemberInfoEntity? = null,
        private val serialVersionUID: Long = 1L
) : Serializable
