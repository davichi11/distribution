package com.distribution.modules.api.pojo.vo;

import com.distribution.modules.account.entity.MemberAccount;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DisMemberInfoAmountsVO {

    private DisMemberInfoEntity disMemberInfoEntity;
    private MemberAccount memberAccount;
    //提现
    private BigDecimal withdrawalAmount;
    //总收入
    private BigDecimal generalIncome;
}
