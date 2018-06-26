package com.distribution.modules.dis.controller;

import com.distribution.common.utils.Result;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.DisMemberInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 用户表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-03
 */
@Slf4j
@RestController
@RequestMapping("dismemberinfo")
public class DisMemberInfoController {
    @Autowired
    private DisMemberInfoService disMemberInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("dismemberinfo:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<DisMemberInfoEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo(() -> disMemberInfoService.queryList(params));

        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("dismemberinfo:info")
    public Result info(@PathVariable("id") String id) {
        DisMemberInfoEntity disMemberInfo = disMemberInfoService.queryObject(id);
        return Result.ok().put("disMemberInfo", disMemberInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("dismemberinfo:save")
    public Result save(@RequestBody DisMemberInfoEntity disMemberInfo) {
        try {
            disMemberInfoService.save(disMemberInfo);
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
    @RequiresPermissions("dismemberinfo:update")
    public Result update(@RequestBody DisMemberInfoEntity disMemberInfo) {
        try {
            disMemberInfoService.update(disMemberInfo);
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
    @RequiresPermissions("dismemberinfo:delete")
    public Result delete(@RequestBody String[] ids) {
        try {
            if (ids.length == 1) {
                disMemberInfoService.delete(ids[0]);
            } else {
                disMemberInfoService.deleteBatch(ids);
            }
        } catch (Exception e) {
            log.error("删除异常", e);
            Result.error("删除异常");
        }
        return Result.ok();
    }

}
