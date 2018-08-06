package com.distribution.modules.dis.vo


data class CardOrderInfoVO(
        /**
         * 真实姓名
         */
        var orderName: String = "",

        /**
         *
         */
        var orderId: String = "",

        /**
         * 手机号
         */
        var orderMobile: String = "",

        /**
         * 身份证
         */
        var orderIdcardno: String = "",

        /**
         *
         */
        var orderEmail: String = "",

        /**
         * 信用卡编号
         */
        var cardNum: String = "",

        /**
         * 银行编号
         */
        var bankNum: String = "",

        /**
         * 订单状态 0:失败,1:成功,2:申请中
         */
        var orderStatus: Int = 0
) {
    object OrderStatus {
        val LOSE = 0//失败
        val SUCCESS = 1//成功
        val APPLICATION = 2//申请中
    }
}
