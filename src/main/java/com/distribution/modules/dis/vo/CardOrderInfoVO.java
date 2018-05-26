package com.distribution.modules.dis.vo;

import lombok.Data;

@Data
public class CardOrderInfoVO {
    private String id;
    /**
     *
     */
    private String orderName;
    /**
     *
     */
    private String orderId;
    /**
     *
     */
    private String orderMobile;
    /**
     *
     */
    private String orderIdcardno;
    /**
     *
     */
    private String orderEmail;
    /**
     * 订单状态 0:失败,1:成功,2:申请中
     */
    private Integer orderStatus;
    /**
     *
     */
    private String isDelete;
    /**
     *
     */
    private String addTime;
    /**
     *
     */
    private String updateTime;

    public static final class OrderStatus{
        public static final int LOSE = 0;//失败
        public static final int SUCCESS = 1;//成功
        public static final int APPLICATION = 2;//申请中
    }
}
