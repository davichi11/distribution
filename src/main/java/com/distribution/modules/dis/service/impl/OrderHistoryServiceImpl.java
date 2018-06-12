package com.distribution.modules.dis.service.impl;

import com.distribution.modules.dis.dao.OrderHistoryMapper;
import com.distribution.modules.dis.entity.OrderHistory;
import com.distribution.modules.dis.service.OrderHistoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.dis.service.impl
 * @Description TODO(描述)
 * @create 2018/6/12-21:54
 */
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {
    @Autowired
    private OrderHistoryMapper orderHistoryMapper;

    /**
     * 保存
     *
     * @param record
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(OrderHistory record) throws Exception {
        orderHistoryMapper.insertSelective(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Integer status, String orderId) throws Exception {
        if (status == null || StringUtils.isBlank(orderId)) {
            return;
        }
        orderHistoryMapper.updateOrderStatus(status, orderId);
    }

    /**
     * 根据订单号查询
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderHistory selectByOrderId(String orderId) {
        return orderHistoryMapper.selectByOrderId(orderId);
    }

    /**
     * 根据支付宝账号查询
     *
     * @param account
     * @return
     */
    @Override
    public List<OrderHistory> selectByAccount(String account) {
        return orderHistoryMapper.selectByAccount(account);
    }

    /**
     * 根据手机号查询
     *
     * @param mobile
     * @return
     */
    @Override
    public List<OrderHistory> selectByMobile(String mobile) {
        return orderHistoryMapper.selectByMobile(mobile);
    }
}
