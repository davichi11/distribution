package com.distribution.modules.dis.entity

import com.distribution.common.utils.DateUtils
import com.distribution.modules.api.entity.UserEntity
import java.io.Serializable
import java.time.LocalDateTime


/**
 * 用户表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-03
 */
data class DisMemberInfoEntity(
        var id: String = "",
        var disPlatformId: Long = 1L,
        var openId: String = "",
        /**
         * 用户id
         */
        var userEntity: UserEntity = UserEntity(),
        /**
         * 上级会员ID
         */
        var parentId: String = "",

        /**
         * 上级会员
         */
        var disMemberParent: DisMemberInfoEntity = DisMemberInfoEntity(),
        /**
         * 下级集合
         */
        var disMemberChildren: List<DisMemberInfoEntity>? = mutableListOf(),

        /**
         * 全路径
         */
        var disFullIndex: String = "",

        /**
         * 真实姓名
         */
        var disUserName: String = "",
        /**
         * 身份证号码
         */
        var idCode: String = "",
        /**
         * 级别
         */
        var disLevel: Int = 0,

        /**
         * 身份类型(0 非会员 1会员)
         */
        var disUserType: String = "0",
        /**
         * 备注
         */
        var disNote: String = "",
        /**
         * 添加时间
         */
        var addTime: String = DateUtils.formatDateTime(LocalDateTime.now()),
        var updateTime: String = DateUtils.formatDateTime(LocalDateTime.now()),
        var isDelete: String = "1"
) : Serializable
