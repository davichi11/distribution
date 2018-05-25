package com.distribution.modules.memeber.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 *    用户金额变化表
 *   
 * @author liuxinxin  
 * @date  11:44  
 * @param   
 * @return   
 */  
@Data
public class MemberAmountHistroy {
    private String id;
    private String hisType;
    private String accountId;
    private BigDecimal hisAmount;
    private String hisNote;
    private String isDelete;
    private String addTime;
    private String updateTime;
    public static final class HisType{
        public static final int EXPEND = 0;//支出
        public static final int INCOME = 1;//收入
    }
}

