package com.distribution.modules.memeber.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 提现信息表
 * 
 * @Auther: liuxinxin
 * @Date: 2018/5/22 22:01
 * @Description: 
 */
@Data
public class WithdrawalInfo {

    private String id;
    private String withdrawNum;
    private String withdrawType;
    private String withdrawName;
    private String withdrawMobile;
    private String withdrawCard;
    private BigDecimal withdrawAmount;
    private BigDecimal withdrawPoundage;
    private BigDecimal realAmount;
    private String aliPayAccount;
    private String isDelete;
    private String addTime;
    private String updateTime;
}
