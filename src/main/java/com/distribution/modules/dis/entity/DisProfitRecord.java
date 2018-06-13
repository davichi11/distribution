package com.distribution.modules.dis.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table dis_profit_record
 *
 * @mbg.generated do_not_delete_during_merge
 */
@Data
public class DisProfitRecord {
    /** */
    private String id;

    /** */
    private String disPlatformId;

    /** */
    private String disGetUserId;

    /** */
    private String disSetUserId;

    /** */
    private BigDecimal disAmount;

    /**
     * 交易类型
     */
    private String disProType;

    /**
     * 备注
     */
    private String disNote;

    /** */
    private String disUserType;

    /**
     * 对应第三方订单编号
     */
    private String disOrderId;

    /** */
    private String isDelete;

    /** */
    private String addTime;

    /** */
    private String updateTime;

}