package com.distribution.modules.dis.controller;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.dis.entity.LoanInfoEntity;
import com.distribution.modules.dis.service.LoanInfoService;
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
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
@Slf4j
@RestController
@RequestMapping("loaninfo")
public class LoanInfoController {
    @Autowired
    private LoanInfoService loanInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("loaninfo:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<LoanInfoEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> loanInfoService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("loaninfo:info")
    public Result info(@PathVariable("id") String id) {
        LoanInfoEntity loanInfo = loanInfoService.queryObject(id);

        return Result.ok().put("loanInfo", loanInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("loaninfo:save")
    public Result save(@RequestBody LoanInfoEntity loanInfo) {
        try {
            loanInfo.setId(CommonUtils.getUUID());
            loanInfo.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
            loanInfoService.save(loanInfo);
        } catch (Exception e) {
            log.error("保存贷款产品信息异常", e);
            return Result.error("保存贷款产品信息异常");
        }

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("loaninfo:update")
    public Result update(@RequestBody LoanInfoEntity loanInfo) {
        try {
            loanInfo.setUpdateTime(DateUtils.formatDateTime(LocalDateTime.now()));
            loanInfoService.update(loanInfo);
        } catch (Exception e) {
            log.error("修改贷款产品信息异常", e);
            return Result.error("修改贷款产品信息异常");
        }
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("loaninfo:delete")
    public Result delete(@RequestBody String[] ids) {
        try {
            if (ids.length == 1) {
                loanInfoService.delete(ids[0]);
            } else {
                loanInfoService.deleteBatch(ids);
            }
        } catch (Exception e) {
            log.error("删除贷款产品信息异常", e);
            return Result.error("删除贷款产品信息异常");
        }
        return Result.ok();
    }

}
