package com.distribution.modules.job.service.impl

import com.distribution.modules.job.dao.ScheduleJobLogDao
import com.distribution.modules.job.entity.ScheduleJobLogEntity
import com.distribution.modules.job.service.ScheduleJobLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("scheduleJobLogService")
class ScheduleJobLogServiceImpl : ScheduleJobLogService {
    @Autowired
    private lateinit var scheduleJobLogDao: ScheduleJobLogDao

    override fun queryObject(jobId: Long): ScheduleJobLogEntity {
        return scheduleJobLogDao.queryObject(jobId)
    }

    override fun queryList(map: Map<String, Any>): List<ScheduleJobLogEntity> {
        return scheduleJobLogDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return scheduleJobLogDao.queryTotal(map)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(log: ScheduleJobLogEntity) {
        scheduleJobLogDao.save(log)
    }

}
