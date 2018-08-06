package com.distribution.modules.api.pojo.vo

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 * @Description TODO(描述)
 * @create 2018/5/26-18:22
 */

data class DisMemberVO(
        /**
         * 上级会员ID
         */
        var parentId: String? = null,

        /**
         * 用户名
         */
        var disUserName: String? = null,


        /**
         * 身份类型(0 非会员 1会员)
         */
        var disUserType: String? = null,


        /**
         * 级别
         */
        var disLevel: Int? = null,

        /**
         * 手机号
         */
        var mobile: String? = null,

        /**
         * 身份证号码
         */
        var idCode: String? = null,

        /**
         * 微信openID
         */
        var openId: String? = null,

        /**
         * 微信昵称
         */
        var nickName: String = "",

        /**
         * 微信头像地址
         */
        var imgUrl: String = "",

        /**
         * 加入时间
         */
        var addTime: String = "",


        var workerId: Long = 0L,


        var token: String = "",


        var expire: Long = 0L
                

)
