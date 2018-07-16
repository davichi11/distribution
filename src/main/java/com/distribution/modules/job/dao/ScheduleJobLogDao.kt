package com.distribution.modules.job.dao

import com.distribution.modules.job.entity.ScheduleJobLogEntity
import com.distribution.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper

/**
 * 定时任务日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月1日 下午10:30:02
 */
@Mapper
interface ScheduleJobLogDao : BaseDao<ScheduleJobLogEntity>
