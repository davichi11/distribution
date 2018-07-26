package com.distribution.modules.pos.controller;

import com.distribution.common.utils.Result;
import com.distribution.modules.pos.entity.PosApplyEntity;
import com.distribution.modules.pos.service.PosApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * POS机申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-06-20
 */
@Slf4j
@RestController
@RequestMapping("posapply")
public class PosApplyController {
    @Autowired
    private PosApplyService posApplyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("posapply:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<PosApplyEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> posApplyService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("posapply:info")
    public Result info(@PathVariable("id") String id) {
        PosApplyEntity posApply = posApplyService.queryObject(id);

        return Result.ok().put("posApply", posApply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("posapply:save")
    public Result save(@RequestBody PosApplyEntity posApply) {
        try {
            posApplyService.save(posApply);
        } catch (Exception e) {
            log.error("保存POS机申请异常", e);
            return Result.error("保存POS机申请异常");
        }

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("posapply:update")
    public Result update(@RequestBody PosApplyEntity posApply) {
        try {
            posApplyService.update(posApply);
        } catch (Exception e) {
            log.error("修改POS机申请异常", e);
            return Result.error("修改POS机申请异常");
        }
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("posapply:delete")
    public Result delete(@RequestBody String[] ids) {
        try {
            if (ids.length == 1) {
                PosApplyEntity applyEntity = posApplyService.queryObject(ids[0]);
                applyEntity.setIsDelete("0");
                posApplyService.update(applyEntity);
            } else {
                Arrays.stream(ids).forEach(id -> {
                    PosApplyEntity applyEntity = posApplyService.queryObject(id);
                    applyEntity.setIsDelete("0");
                    try {
                        posApplyService.update(applyEntity);
                    } catch (Exception e) {
                        log.error("删除POS机申请异常", e);
                    }
                });
            }
        } catch (Exception e) {
            log.error("删除POS机申请异常", e);
            return Result.error("删除POS机申请异常");
        }
        return Result.ok();
    }

}
