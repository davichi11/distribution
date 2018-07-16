package com.distribution.modules.dis.service.impl

import com.distribution.modules.dis.dao.OrderHistoryMapper
import com.distribution.modules.dis.entity.OrderHistory
import com.distribution.modules.dis.service.OrderHistoryService
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.dis.service.impl
 * @Description TODO(描述)
 * @create 2018/6/12-21:54
 */
@Service
class OrderHistoryServiceImpl : OrderHistoryService {
    @Autowired
    private lateinit var orderHistoryMapper: OrderHistoryMapper

    /**
     * 保存
     *
     * @param record
     */
    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(record: OrderHistory) {
        orderHistoryMapper.insertSelective(record)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun updateOrderStatus(status: Int?, orderId: String) {
        if (status == null || StringUtils.isBlank(orderId)) {
            return
        }
        orderHistoryMapper.updateOrderStatus(status, orderId)
    }

    /**
     * 根据订单号查询
     *
     * @param orderId
     * @return
     */
    override fun selectByOrderId(orderId: String): OrderHistory {
        return orderHistoryMapper.selectByOrderId(orderId)
    }

    /**
     * 根据支付宝账号查询
     *
     * @param account
     * @return
     */
    override fun selectByAccount(account: String): List<OrderHistory> {
        return orderHistoryMapper.selectByAccount(account)
    }

    /**
     * 根据手机号查询
     *
     * @param mobile
     * @return
     */
    override fun selectByMobile(mobile: String): List<OrderHistory> {
        return orderHistoryMapper.selectByMobile(mobile)
    }
}
