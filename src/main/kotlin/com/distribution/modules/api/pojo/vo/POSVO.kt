package com.distribution.modules.api.pojo.vo

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 
 * @create 2018/6/20-21:41
 */

data class POSVO(
        /**
         *
         */
        var name: String = "",
        /**
         *
         */
        var mobile: Long = 0L,
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
        var attribute: Int = 0

)
