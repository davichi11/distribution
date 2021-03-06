package com.distribution.modules.account.entity

import java.math.BigDecimal

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table member_account_histroy
 *
 * @mbg.generated do_not_delete_during_merge
 */

data class MemberAccountHistory(
        /**  */
        var id: String = "",

        /**
         * 收入类型，0:支出,1:收入
         */
        var hisType: Boolean = MemberAccountHistory.HisType.INCOME,

        /**
         * 用户金额ID
         */
        var accountId: String = "",
        /**
         * 支付宝账号
         */
        var aliPayAccount: String = "",
        /**
         * 用户姓名
         */
        var userName: String = "",
        /**
         * 手机号
         */
        var mobile: String = "",

        /**  */
        var hisAmount: BigDecimal = BigDecimal.ZERO,

        /**  */
        var hisNote: String = "",

        /**  */
        var isDelete: String = "",

        /**  */
        var addTime: String = "",

        /**  */
        var updateTime: String = ""

) {
    object HisType {
        val EXPEND = false
        val INCOME = true
    }

}