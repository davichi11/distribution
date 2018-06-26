package com.distribution.modules.sys.entity;

import lombok.Data;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.sys.entity
 * @Description TODO(描述)
 * @create 2018/6/20-21:59
 */
@Data
public class District {
    private Integer id;
    private String name;
    private District parent;
    private String code;
    private Integer order;
}
