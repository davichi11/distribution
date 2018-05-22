package com.distribution.modules.job.service.impl;

import com.distribution.modules.job.dao.ScheduleJobLogDao;
import com.distribution.modules.job.entity.ScheduleJobLogEntity;
import com.distribution.modules.job.service.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
    @Autowired
    private ScheduleJobLogDao scheduleJobLogDao;

    @Override
    public ScheduleJobLogEntity queryObject(Long jobId) {
        return scheduleJobLogDao.queryObject(jobId);
    }

    @Override
    public List<ScheduleJobLogEntity> queryList(Map<String, Object> map) {
        return scheduleJobLogDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return scheduleJobLogDao.queryTotal(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ScheduleJobLogEntity log) throws Exception {
        scheduleJobLogDao.save(log);
    }

}
