package com.distribution.modules.article.entity

import com.distribution.common.utils.DateUtils
import java.io.Serializable
import java.time.LocalDateTime


/**
 * 文章表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-29
 */
data class ArticleInfoEntity(
        /**
         *
         */
        var id: Int? = null,
        /**
         * 文章标题
         */
        var title: String = "",
        /**
         * 文章内容
         */
        var detail: String = "",
        /**
         * 文章类别 0:客户推广 1:热门活动
         */
        var type: String = "0",
        /**
         *
         */
        var order: Int = 0,
        /**
         *
         */
        var addTime: String = DateUtils.formatDateTime(LocalDateTime.now()),
        /**
         *
         */
        var isDelete: String = "1"

) : Serializable {
    companion object {
        const val serialVersionUID = 1L
    }
}
