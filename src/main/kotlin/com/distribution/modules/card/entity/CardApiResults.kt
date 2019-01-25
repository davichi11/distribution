package com.distribution.modules.card.entity

import java.io.Serializable

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.card.entity
 * @Description TODO(描述)
 * @create 2018/6/8-20:48
 */

data class CardApiResults(
        private val serialVersionUID: Long = 1L,
        /**
         * maxTime : 最高期限
         */
        var maxTime: String = "",

        /**
         * highestMoney : 最高额度
         */
        var highestMoney: String = "",

        /**
         * 背景图片
         */
        var backgroundImg: String = "",

        /**
         * 最低期限
         */
        var minTime: String = "",

        /**
         * 办卡人数
         */
        var num: String = "",

        /**
         * 名称
         */
        var name: String = "",

        /**
         * 链接
         */
        var link: String = "",

        /**
         * logo图
         */
        var icon: String = "",

        /**
         * 利率
         */
        var persent: String = "",

        /**
         * 产品编号
         */
        var id: String = "",

        var type: Int = 0,

        /**
         * 内容
         */
        var content: String = "",


        var moneySent: Int = 0

) : Serializable
