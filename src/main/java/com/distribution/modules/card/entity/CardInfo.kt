package com.distribution.modules.card.entity

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table card_info
 *
 * @mbg.generated do_not_delete_during_merge
 */

data class CardInfo(
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
        var updateTime: String = "",


        /**  */
        var addTime: String = "",


        /**  */
        var isDelete: String = ""
                             
)