package com.distribution.modules.sys.entity

import java.io.Serializable


/**
 * 系统配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月4日 下午6:43:36
 */
data class SysConfigEntity(
        private val serialVersionUID: Long = 1L,
        var id: Long = 0,
        var key: String = "",
        var value: String = "",
        var remark: String = ""
) : Serializable

