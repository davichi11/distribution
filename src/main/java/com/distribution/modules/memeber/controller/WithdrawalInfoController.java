package com.distribution.modules.memeber.controller;

import com.distribution.common.utils.Result;
import com.distribution.modules.memeber.entity.WithdrawalInfo;
import com.distribution.modules.memeber.service.WithdrawalInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 提现信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-30
 */
@Slf4j
@RestController
@RequestMapping("withdrawal")
public class WithdrawalInfoController {
    @Autowired
    private WithdrawalInfoService withdrawalInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("withdrawalinfo:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<WithdrawalInfo> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo(() -> withdrawalInfoService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("withdrawalinfo:info")
    public Result info(@PathVariable("id") String id) {
        WithdrawalInfo withdrawalInfo = withdrawalInfoService.querySingle(id);

        return Result.ok().put("withdrawalInfo", withdrawalInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("withdrawalinfo:save")
    public Result save(@RequestBody WithdrawalInfo withdrawalInfo) {
        try {
            withdrawalInfoService.save(withdrawalInfo);
        } catch (Exception e) {
            log.error("保存异常", e);
            Result.error("保存异常");
        }

        return Result.ok();
    }


}
