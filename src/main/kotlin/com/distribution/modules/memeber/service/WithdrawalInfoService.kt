package com.distribution.modules.memeber.service

import com.distribution.modules.memeber.entity.WithdrawalInfo

import java.math.BigDecimal

interface WithdrawalInfoService {

    /**
     * 根据用户手机号查询用户提现金额
     *
     * @param withdrawMobile
     * @return
     */
    fun withdrawalAmounts(withdrawMobile: String): BigDecimal

    /**
     * 提现记录查询
     *
     * @param params
     * @return
     */
    fun queryList(params: Map<String, Any>): List<WithdrawalInfo>

    /**
     * 添加提现信息
     *
     * @param withdrawalInfo
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(withdrawalInfo: WithdrawalInfo)

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    fun querySingle(id: String): WithdrawalInfo
}
