package com.distribution.modules.api.pojo.vo

import io.swagger.annotations.ApiModel
import java.io.Serializable

/**
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 
 * @author ChunLiang Hu
 * @create 2018/8/18-23:03
 */
@ApiModel(description = "意见反馈")
data class FeedBackVO(
        /**
         *
         */
        var id: Long? = null,
        /**
         * 会员id
         */
        var mobile: String? = null,
        /**
         * 反馈标题
         */
        var title: String? = null,
        /**
         * 反馈内容
         */
        var content: String? = null,
        private val serialVersionUID: Long = 22L
) : Serializable