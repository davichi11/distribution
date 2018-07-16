package com.distribution.modules.memeber.entity

import lombok.Data
import java.math.BigDecimal
import javax.validation.constraints.NotNull

/**
 * 提现信息表
 *
 * @Auther: liuxinxin
 * @Date: 2018/5/22 22:01
 * @Description:
 */

class WithdrawalInfo {

    var id: String? = null
        set(id) {
            field = this.id
        }
    var withdrawNum: String? = null
        set(withdrawNum) {
            field = this.withdrawNum
        }
    /**
     * 提现状态 0:失败,1:成功
     */
    var withdrawType: String? = null
        set(withdrawType) {
            field = this.withdrawType
        }
    /**
     * 提现金额
     */
    @NotNull
    var withdrawAmount: BigDecimal? = null
        set(withdrawAmount) {
            field = this.withdrawAmount
        }
    var withdrawName: String? = null
        set(withdrawName) {
            field = this.withdrawName
        }
    @NotNull
    var withdrawMobile: String? = null
        set(withdrawMobile) {
            field = this.withdrawMobile
        }
    var withdrawCard: String? = null
        set(withdrawCard) {
            field = this.withdrawCard
        }
    var withdrawPoundage: BigDecimal? = null
        set(withdrawPoundage) {
            field = this.withdrawPoundage
        }
    var realAmount: BigDecimal? = null
        set(realAmount) {
            field = this.realAmount
        }
    var aliPayAccount: String? = null
        set(aliPayAccount) {
            field = this.aliPayAccount
        }
    var isDelete: String? = null
        set(isDelete) {
            field = this.isDelete
        }
    var addTime: String? = null
        set(addTime) {
            field = this.addTime
        }
    var updateTime: String? = null
        set(updateTime) {
            field = this.updateTime
        }
}
