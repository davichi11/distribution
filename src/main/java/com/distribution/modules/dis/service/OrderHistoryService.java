package com.distribution.modules.dis.service;

import com.distribution.modules.dis.entity.OrderHistory;

import java.util.List;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.dis.service
 * @Description TODO(描述)
 * @create 2018/6/12-21:52
 */
public interface OrderHistoryService {
    /**
     * 保存
     * @param record
     */
    void save(OrderHistory record) throws Exception;

    void updateOrderStatus(Integer status, String orderId) throws Exception;

    /**
     * 根据订单号查询
     * @param orderId
     * @return
     */
    OrderHistory selectByOrderId(String orderId);

    /**
     * 根据支付宝账号查询
     * @param account
     * @return
     */
    List<OrderHistory> selectByAccount(String account);

    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    List<OrderHistory> selectByMobile(String mobile);
}
