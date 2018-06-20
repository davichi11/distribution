package com.distribution.modules.api.pojo.vo;

import lombok.Data;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 * @Description TODO(描述)
 * @create 2018/6/20-21:41
 */
@Data
public class POSVO {
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
}
