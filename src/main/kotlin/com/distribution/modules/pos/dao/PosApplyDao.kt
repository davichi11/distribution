package com.distribution.modules.pos.dao

import com.distribution.modules.pos.entity.PosApplyEntity
import com.distribution.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper

/**
 * POS机申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-06-20
 */
@Mapper
interface PosApplyDao : BaseDao<PosApplyEntity>
