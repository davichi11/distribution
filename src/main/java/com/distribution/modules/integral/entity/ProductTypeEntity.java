package com.distribution.modules.integral.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 积分兑换产品类型表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
@Data
public class ProductTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String id;
    /**
     * 产品类型名称
     */
    private String prodName;
    /**
     * 结算周期
     */
    private String prodRate;
    /**
     * 产品描述
     */
    private String prodRemark;

    /**
     * 图标
     */
    private String prodImg;

    private String prodType;
}
