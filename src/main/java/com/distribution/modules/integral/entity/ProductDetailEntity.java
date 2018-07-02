package com.distribution.modules.integral.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 积分兑换产品列表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
@Data
public class ProductDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String id;
    /**
     * 产品名称
     */
    private String prodDetailName;
    /**
     * 回购价
     */
    private Integer prodDetailBuyBack;
    /**
     * 产品类型关联ID
     */
    private String prodTypeId;

    private String typeName;
    /**
     * 产品积分数
     */
    private Long prodDetailValue;
    /**
     * 兑换次数
     */
    private Integer prodDetailCount;
}
