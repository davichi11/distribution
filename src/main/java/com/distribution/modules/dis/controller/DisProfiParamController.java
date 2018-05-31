package com.distribution.modules.dis.controller;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.dis.entity.DisProfiParam;
import com.distribution.modules.dis.service.DisProfiParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;


/**
 * 分润参数设置
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-22
 */
@Slf4j
@RestController
@RequestMapping("disprofiparam")
public class DisProfiParamController {
    @Autowired
    private DisProfiParamService disProfiParamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("disprofiparam:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<DisProfiParam> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo(() -> disProfiParamService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("disprofiparam:info")
    public Result info(@PathVariable("id") String id) {
        DisProfiParam disProfiParam = disProfiParamService.queryObject(id);

        return Result.ok().put("disProfiParam", disProfiParam);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("disprofiparam:save")
    public Result save(@RequestBody DisProfiParam disProfiParam) {
        disProfiParam.setId(CommonUtils.getUUID());
        disProfiParam.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
        try {
            disProfiParamService.save(disProfiParam);
        } catch (Exception e) {
            log.error("保存异常", e);
            Result.error("保存异常");
        }

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("disprofiparam:update")
    public Result update(@RequestBody DisProfiParam disProfiParam) {
        disProfiParam.setUpdateTime(DateUtils.formatDateTime(LocalDateTime.now()));
        try {
            disProfiParamService.update(disProfiParam);
        } catch (Exception e) {
            log.error("修改异常", e);
            Result.error("修改异常");
        }
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("disprofiparam:delete")
    public Result delete(@RequestBody String[] ids) {
        try {
            if (ids.length == 1) {
                disProfiParamService.delete(ids[0]);
            } else {
                disProfiParamService.deleteBatch(ids);
            }
        } catch (Exception e) {
            log.error("删除分润参数异常", e);
            Result.error("删除分润参数异常");
        }
        return Result.ok();
    }

}
