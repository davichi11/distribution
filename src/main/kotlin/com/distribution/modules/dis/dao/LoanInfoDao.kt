package com.distribution.modules.dis.dao

import com.distribution.modules.dis.entity.LoanInfoEntity
import com.distribution.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper

/**
 *
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
@Mapper
interface LoanInfoDao : BaseDao<LoanInfoEntity> {
    fun countByParam(param: Map<String, Any>): Int
}
