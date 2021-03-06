package com.distribution.modules.account.entity

import com.distribution.modules.dis.entity.DisMemberInfoEntity
import java.math.BigDecimal

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table member_account
 *
 * @mbg.generated do_not_delete_during_merge
 */

data class MemberAccount(
        /**
         * 主键ID
         */
        var accountId: String = "",


        /**
         * 会员
         */
        var member: DisMemberInfoEntity = DisMemberInfoEntity(),

        /**
         * 账户类型
         */
        var memberType: String = "",
        /**
         * 支付宝账户
         */
        var aliPayAccount: String = "",

        /**  */
        var memberAmount: BigDecimal = BigDecimal.ZERO,

        /**  */
        var isDelete: String = "1",

        /**  */
        var addTime: String = "",

        /**  */
        var updateTime: String = ""
)