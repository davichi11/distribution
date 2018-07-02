package com.distribution.modules.integral.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 积分兑换申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
@Data
public class IntegralOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String id;
    /**
     * 兑换人手机号
     */
    private Long mobile;
    /**
     * 兑换产品ID
     */
    private String detailId;

    private String prodName;
    /**
     * 卷码图片,可以逗号分隔
     */
    private String img;
    /**
     * 申请时间
     */
    private String addTime;
    /**
     * 审核状态
     */
    private int status;
}
