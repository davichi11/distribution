package com.distribution.modules.dis.entity

import com.distribution.common.utils.DateUtils
import com.distribution.modules.card.entity.CardInfo
import java.io.Serializable
import java.time.LocalDateTime


/**
 * 办卡订单信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */

data class CardOrderInfoEntity(
        /**
         *
         */
        var id: String = "",

        /**
         *
         */
        var orderName: String = "",

        /**
         *
         */
        var orderId: String = "",

        /**
         *
         */
        var orderMobile: String = "",

        /**
         *
         */
        var orderIdcardno: String = "",

        /**
         *
         */
        var orderEmail: String = "",

        /**
         * 订单状态 0:失败,1:成功,2:申请中
         */
        var orderStatus: Int = 0,

        /**
         * 用户关联ID
         */
        var memberInfo: DisMemberInfoEntity? = null,

        /**
         * 关联信用卡ID
         */
        var cardInfo: CardInfo? = null,

        /**
         *
         */
        var isDelete: String = "1",

        /**
         *
         */
        var addTime: String = DateUtils.formatDateTime(LocalDateTime.now()),

        /**
         *
         */
        var updateTime: String = DateUtils.formatDateTime(LocalDateTime.now())


) : Serializable
