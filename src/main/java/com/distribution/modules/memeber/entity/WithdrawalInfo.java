package com.distribution.modules.memeber.entity;

import lombok.Data;

<<<<<<< HEAD
=======
import javax.validation.constraints.NotNull;
>>>>>>> origin/develop
import java.math.BigDecimal;

/**
 * 提现信息表
<<<<<<< HEAD
 * 
 * @Auther: liuxinxin
 * @Date: 2018/5/22 22:01
 * @Description: 
=======
 *
 * @Auther: liuxinxin
 * @Date: 2018/5/22 22:01
 * @Description:
>>>>>>> origin/develop
 */
@Data
public class WithdrawalInfo {

    private String id;
    private String withdrawNum;
<<<<<<< HEAD
    private String withdrawType;
    private String withdrawName;
    private String withdrawMobile;
    private String withdrawCard;
    private BigDecimal withdrawAmount;
    private BigDecimal withdrawPoundage;
    private BigDecimal realAmount;
=======
    /**
     * 提现状态 0:失败,1:成功
     */
    private String withdrawType;
    /**
     * 提现金额
     */
    @NotNull
    private BigDecimal withdrawAmount;
    private String withdrawName;
    @NotNull
    private String withdrawMobile;
    private String withdrawCard;
    private BigDecimal withdrawPoundage;
    private BigDecimal realAmount;
    private String aliPayAccount;
>>>>>>> origin/develop
    private String isDelete;
    private String addTime;
    private String updateTime;
}
