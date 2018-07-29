package com.distribution.modules.pos.entity

import com.distribution.common.utils.DateUtils
import java.io.Serializable
import java.time.LocalDateTime


/**
 * POS机申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-06-20
 */
data class PosApplyEntity(
        var id: String = "",
        var name: String = "",
        var mobile: Long = 0,
        /**
         * 商户名称
         */
        var merchants: String = "",
        /**
         * 区域编码
         */
        var areaCode: String = "",
        /**
         * 连锁属性 0:非连锁,1:连锁
         */
        var attribute: Int = 0,
        var addTime: String = DateUtils.formatDateTime(LocalDateTime.now()),
        var isDelete: String = "1"
) : Serializable
