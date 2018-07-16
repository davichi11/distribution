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
        var userId: String = "",

        /**
         * 用户名
         */
        var username: String = "",

        /**
         * 手机号
         */
        var mobile: String = "",

        /**
         * 密码
         */
        @Transient
        var password: String = "",
        /**
         * 创建时间
         */
        var createTime: LocalDateTime = LocalDateTime.now(),

        var memberInfo: DisMemberInfoEntity = DisMemberInfoEntity()
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }

}
