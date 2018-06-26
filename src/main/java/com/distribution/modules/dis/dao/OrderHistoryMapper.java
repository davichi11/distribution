package com.distribution.modules.dis.dao;

import com.distribution.modules.dis.entity.OrderHistory;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_history
     *
     * @mbg.generated
     */
    int insert(OrderHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_history
     *
     * @mbg.generated
     */
    int insertSelective(OrderHistory record);

    void updateOrderStatus(@Param("status")Integer status, @Param("orderId")String orderId);

    OrderHistory selectByOrderId(String orderId);

    List<OrderHistory> selectByAccount(String account);

    List<OrderHistory> selectByMobile(String mobile);
}