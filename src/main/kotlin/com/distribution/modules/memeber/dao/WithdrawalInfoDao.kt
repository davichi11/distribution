package com.distribution.modules.memeber.dao

import com.distribution.modules.memeber.entity.WithdrawalInfo
import com.distribution.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper

@Mapper
interface WithdrawalInfoDao : BaseDao<WithdrawalInfo> {

    fun findByAliPayAccount(withdrawMobile: String): List<WithdrawalInfo>
}
