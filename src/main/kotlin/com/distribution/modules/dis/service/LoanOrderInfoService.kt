package com.distribution.modules.dis.service

import com.distribution.modules.dis.entity.LoanOrderInfoEntity


/**
 * 贷款订单
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
interface LoanOrderInfoService {
    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: String): LoanOrderInfoEntity

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<LoanOrderInfoEntity>

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(loanOrderInfo: LoanOrderInfoEntity)

    /**
     * 更新
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(loanOrderInfo: LoanOrderInfoEntity)

    /**
     * 删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: String)

    /**
     * 批量删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(ids: Array<String>)

    fun countLoanOrder(map: Map<String, Any>): Int
}
