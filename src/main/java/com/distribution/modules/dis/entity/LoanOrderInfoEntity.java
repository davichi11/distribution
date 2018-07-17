package com.distribution.modules.dis.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 贷款订单
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
@Data
public class LoanOrderInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String id;
    /**
     * 订单编号
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
     * 订单状态 0失败 1成功 2申请中
     */
    private Integer orderStatus;
    /**
     *
     */
    private String memberId;
    /**
     *
     */
    private String loanId;

    private String loanName;

    private String userName;
    /**
     *
     */
    private Double loanAmount;
    /**
     *
     */
    private Integer isDelete;
    /**
     *
     */
    private String addTime;
    /**
     *
     */
    private String updateTime;


    private Double loanMoney;
}
