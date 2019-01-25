package com.distribution.modules.dis.dao

import com.distribution.modules.dis.entity.LoanOrderInfoEntity
import com.distribution.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper

/**
 * 贷款订单
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
@Mapper
interface LoanOrderInfoDao : BaseDao<LoanOrderInfoEntity> {
    fun queryCount(param: Map<String, Any>): Int
}
