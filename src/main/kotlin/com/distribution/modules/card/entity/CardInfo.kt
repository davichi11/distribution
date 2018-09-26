package com.distribution.modules.card.entity

import com.distribution.common.utils.DateUtils
import java.io.Serializable
import java.time.LocalDateTime

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table card_info
 *
 * @mbg.generated do_not_delete_during_merge
 */

data class CardInfo(
        private val serialVersionUID: Long = 1L,
        /**  */
        var id: String = "",


        /**
         * 银行代号
         */
        var bankNum: String = "",


        /**
         * 信用卡代号
         */
        var cardNum: String = "",


        /**
         * 信用卡名称
         */
        var cardName: String = "",


        /**
         * 信用卡图片
         */
        var cardImg: String = "",


        /**
         * 信用卡详情
         */
        var cardInfo: String = "",


        /**
         * 信用卡办理链接
         */
        var cardUrl: String = "",

        /**
         * 佣金返利
         */
        var rebate: Double = 0.00,


        /**  */
        var updateTime: String = DateUtils.formatDateTime(LocalDateTime.now()),


        /**  */
        var addTime: String = DateUtils.formatDateTime(LocalDateTime.now()),


        /**  */
        var isDelete: String = "1",
        /**
         * 是否启用推送第三方数据 0否 1是
         */
        var useThirdPart: Int = 1


) : Serializable