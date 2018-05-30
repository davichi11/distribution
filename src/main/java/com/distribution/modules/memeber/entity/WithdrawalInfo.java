package com.distribution.modules.memeber.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
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
    /**
     * 提现状态 0:失败,1:成功
     */
    private String withdrawType;
    private String withdrawName;
    @NotNull
    private String withdrawMobile;
    private String withdrawCard;
    @NotNull
    private BigDecimal withdrawAmount;
    private BigDecimal withdrawPoundage;
    private BigDecimal realAmount;
    private String aliPayAccount;
    private String isDelete;
    private String addTime;
    private String updateTime;
}
