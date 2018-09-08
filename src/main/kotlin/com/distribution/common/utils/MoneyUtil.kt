package com.distribution.common.utils

object MoneyUtil {


    fun convertMoney2Cent(yuan: String): Long {
        val money = Money(yuan)
        return money.cent
    }

//    @JvmStatic
//    fun main(args: Array<String>) {
//        println(convertMoney2Cent("20.02"))
//    }

}
