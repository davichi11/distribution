package com.distribution.modules.integral.service

import com.distribution.modules.integral.entity.IntegralOrderEntity


/**
 * 积分兑换申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
interface IntegralOrderService {
    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: String): IntegralOrderEntity

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<IntegralOrderEntity>

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(integralOrder: IntegralOrderEntity)

    /**
     * 更新
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(integralOrder: IntegralOrderEntity)

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
}
