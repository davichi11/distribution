package com.distribution.modules.pos.entity;

import com.distribution.common.utils.DateUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * POS机申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-06-20
 */
@Data
public class PosApplyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String id;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Long mobile;
    /**
     * 商户名称
     */
    private String merchants;
    /**
     * 区域编码
     */
    private String areaCode;
    /**
     * 连锁属性 0:非连锁,1:连锁
     */
    private Integer attribute;

    private String addTime = DateUtils.formatDateTime(LocalDateTime.now());

    private String isDelete = "1";
}
