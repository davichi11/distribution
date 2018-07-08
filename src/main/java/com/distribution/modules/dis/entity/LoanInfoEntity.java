package com.distribution.modules.dis.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
@Data
public class LoanInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String id;
    /**
     * 贷款产品名称
     */
    private String loanName;
    /**
     * 产品链接
     */
    private String loanUrl;
    /**
     *
     */
    private String loanImg;
    /**
     *
     */
    private String addTime;
    /**
     *
     */
    private String updateTime;
    /**
     * 是否启用 0否 1是
     */
    private Integer isDelete;
    /**
     *
     */
    private String remark;
}
