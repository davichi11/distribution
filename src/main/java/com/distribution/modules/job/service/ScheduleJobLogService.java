package com.distribution.modules.job.service;

import com.distribution.modules.job.entity.ScheduleJobLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月1日 下午10:34:48
 */
public interface ScheduleJobLogService {

    /**
     * 根据ID，查询定时任务日志
     *
     * @param jobId
     * @return
     */
    ScheduleJobLogEntity queryObject(Long jobId);

    /**
     * 查询定时任务日志列表
     *
     * @param map
     * @return
     */
    List<ScheduleJobLogEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存定时任务日志
     *
     * @param log
     * @throws Exception
     */
    void save(ScheduleJobLogEntity log) throws Exception;

}
