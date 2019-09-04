package com.distribution.modules.api.pojo.vo

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 
 * @create 2018/5/11-23:12
 */

data class DisFansVO(
        /**
         * 微信ID
         */
        var wechatId: String = "",


        /**
         * 微信头像
         */
        var wechatImg: String = "",

        /**
         * 微信昵称
         */
        var wechatNickname: String = "",

        /**
         * 推荐人ID
         */
        var memberId: String = "",

        var mobile: String = "",


        var token: String = ""
                
)
