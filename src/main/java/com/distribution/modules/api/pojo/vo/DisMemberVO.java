package com.distribution.modules.api.pojo.vo;

import lombok.Data;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.pojo.vo
 * @Description TODO(描述)
 * @create 2018/5/26-18:22
 */
@Data
public class DisMemberVO {
    /**
     * 上级会员ID
     */
    private String parentId;
    /**
     *用户名
     */
    private String disUserName;

    /**
     * 身份类型(0 非会员 1会员)
     */
    private String disUserType;

    /**
     * 级别
     */
    private Integer disLevel;

}
