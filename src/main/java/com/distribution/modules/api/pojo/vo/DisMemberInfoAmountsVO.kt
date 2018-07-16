package com.distribution.modules.api.pojo.vo

import com.distribution.modules.account.entity.MemberAccount
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import java.math.BigDecimal


data class DisMemberInfoAmountsVO(

        var disMemberInfoEntity: DisMemberInfoEntity = DisMemberInfoEntity(),

        var memberAccount: MemberAccount = MemberAccount(),

        //提现
        var withdrawalAmount: BigDecimal = BigDecimal.ZERO,

        //总收入
        var generalIncome: BigDecimal = BigDecimal.ZERO

)
