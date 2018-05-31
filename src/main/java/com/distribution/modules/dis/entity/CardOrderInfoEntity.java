package com.distribution.modules.dis.entity;

import com.distribution.modules.card.entity.CardInfo;
import lombok.Data;

import java.io.Serializable;


/**
 * 办卡订单信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */
@Data
public class CardOrderInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
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
     * 用户关联ID
     */
    private DisMemberInfoEntity memberInfo;
    /**
     * 关联信用卡ID
     */
    private CardInfo cardInfo;
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

    public static final class OrderStatus {
        public static final int LOSE = 0;//失败
        public static final int SUCCESS = 1;//成功
        public static final int APPLICATION = 2;//申请中
    }
}
