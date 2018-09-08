package com.distribution.modules.dis.entity

import java.io.Serializable


/**
 * 意见反馈表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-08-18
 */
data class FeedbackEntity(
        /**
         *
         */
        var id: Long? = null,
        /**
         * 会员id
         */
        var member: DisMemberInfoEntity? = null,
        /**
         * 反馈标题
         */
        var title: String? = null,
        /**
         * 反馈内容
         */
        var content: String? = null,
        /**
         *
         */
        var addTime: String? = null,
        /**
         * 是否显示 0否 1是
         */
        var isDelete: Int? = null,
        private val serialVersionUID: Long = 1L
) : Serializable {


}
