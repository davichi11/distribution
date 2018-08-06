package com.distribution.modules.dis.entity

import com.distribution.modules.api.entity.UserEntity
import java.io.Serializable


/**
 * 用户表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-03
 */
data class DisMemberInfoEntity(
        private val serialVersionUID: Long = 1L,
        var id: String? = null,
        var disPlatformId: Long? = null,
        var openId: String? = null,
        /**
         * 用户id
         */
        var userEntity: UserEntity? = null,
        /**
         * 上级会员ID
         */
        var parentId: String? = null,

        /**
         * 上级会员
         */
        var disMemberParent: DisMemberInfoEntity? = null,
        /**
         * 下级集合
         */
        var disMemberChildren: List<DisMemberInfoEntity>? = mutableListOf(),

        /**
         * 全路径
         */
        var disFullIndex: String? = null,

        /**
         * 真实姓名
         */
        var disUserName: String? = null,
        /**
         * 身份证号码
         */
        var idCode: String? = null,
        /**
         * 级别
         */
        var disLevel: Int? = null,

        /**
         * 身份类型(0 非会员 1会员)
         */
        var disUserType: String? = null,
        /**
         * 备注
         */
        var disNote: String? = null,
        /**
         * 添加时间
         */
        var addTime: String? = null,
        var updateTime: String? = null,
        var isDelete: String? = null
) : Serializable
