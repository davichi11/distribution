package com.distribution.modules.article.entity

import com.distribution.common.utils.DateUtils
import java.io.Serializable
import java.time.LocalDateTime


/**
 * 轮播图
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-29
 */
data class AdPictureEntity(
        /**
         *
         */
        var id: Int? = null,
        /**
         *
         */
        var imgUrl: String = "",
        /**
         * 对应的文章编号
         */
        var articleId: Int? = null,
        /**
         * 对应的文章标题
         */
        var articleTitle: String? = null,
        /**
         * 排序
         */
        var order: Int = 0,
        /**
         *
         */
        var addTime: String = DateUtils.formatDateTime(LocalDateTime.now()),
        /**
         * 是否启用 0否 1是
         */
        var isDelete: String = "1"

) : Serializable {
    companion object {
        const val serialVersionUID = 1L
    }
}
