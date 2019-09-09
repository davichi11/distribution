package com.distribution.modules.dis.service

import com.distribution.modules.dis.entity.OrderHistory

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.dis.service
 
 * @create 2018/6/12-21:52
 */
interface OrderHistoryService {
    /**
     * 保存
     * @param record
     */
    @Throws(Exception::class)
    fun save(record: OrderHistory)

    @Throws(Exception::class)
    fun updateOrderStatus(status: Int?, orderId: String)

    /**
     * 根据订单号查询
     * @param orderId
     * @return
     */
    fun selectByOrderId(orderId: String): OrderHistory?

    /**
     * 根据支付宝账号查询
     * @param account
     * @return
     */
    fun selectByAccount(account: String): List<OrderHistory>

    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    fun selectByMobile(mobile: String): List<OrderHistory>
}
