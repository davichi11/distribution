package com.distribution.modules.job.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.distribution.common.annotation.SysLog;
import com.distribution.common.utils.Result;
import com.distribution.common.validator.ValidatorUtils;
import com.distribution.modules.job.entity.ScheduleJobEntity;
import com.distribution.modules.job.service.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 定时任务
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月28日 下午2:16:40
 */
@Slf4j
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<ScheduleJobEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> scheduleJobService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    public Result info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);

        return Result.ok().put("schedule", schedule);
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    public Result save(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);
        scheduleJob.setCreateTime(LocalDateTime.now());
        try {
            scheduleJobService.save(scheduleJob);
        } catch (Exception e) {
            log.error("更新定时任务异常", e);
            return Result.error("更新定时任务异常");
        }

        return Result.ok();
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    public Result update(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        try {
            scheduleJobService.update(scheduleJob);
        } catch (Exception e) {
            log.error("更新定时任务异常", e);
            return Result.error("更新定时任务异常");
        }

        return Result.ok();
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    public Result delete(@RequestBody Long[] jobIds) {
        try {
            scheduleJobService.deleteBatch(jobIds);
        } catch (Exception e) {
            log.error("删除定时任务异常", e);
            return Result.error("删除定时任务异常");
        }

        return Result.ok();
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    public Result run(@RequestBody Long[] jobIds) {
        try {
            scheduleJobService.run(jobIds);
        } catch (Exception e) {
            log.error("执行任务异常", e);
            return Result.error("执行任务异常");
        }

        return Result.ok();
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    public Result pause(@RequestBody Long[] jobIds) {
        try {
            scheduleJobService.pause(jobIds);
        } catch (Exception e) {
            log.error("暂停任务异常", e);
            Result.error("暂停任务异常");
        }

        return Result.ok();
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    public Result resume(@RequestBody Long[] jobIds) {
        try {
            scheduleJobService.resume(jobIds);
        } catch (Exception e) {
            log.error("恢复定时任务异常", e);
            return Result.error("恢复定时任务异常");
        }

        return Result.ok();
    }

}
