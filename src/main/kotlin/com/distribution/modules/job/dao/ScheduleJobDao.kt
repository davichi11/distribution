package com.distribution.modules.job.dao

import com.distribution.modules.job.entity.ScheduleJobEntity
import com.distribution.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper

/**
 * 定时任务
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月1日 下午10:29:57
 */
@Mapper
interface ScheduleJobDao : BaseDao<ScheduleJobEntity> {

    /**
     * 批量更新状态
     */
    fun updateBatch(map: Map<String, Any>): Int
}
