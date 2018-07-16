package com.distribution.modules.job.controller

import com.distribution.common.utils.Result
import com.distribution.modules.job.entity.ScheduleJobLogEntity
import com.distribution.modules.job.service.ScheduleJobLogService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 定时任务日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月1日 下午10:39:52
 */
@RestController
@RequestMapping("/sys/scheduleLog")
class ScheduleJobLogController {
    @Autowired
    private lateinit var scheduleJobLogService: ScheduleJobLogService

    /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    fun list(@RequestParam params: Map<String, Any>): Result? {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<ScheduleJobLogEntity> { scheduleJobLogService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    fun info(@PathVariable("logId") logId: Long): Result? {
        val log = scheduleJobLogService.queryObject(logId)

        return Result().ok().put("log", log)
    }
}
