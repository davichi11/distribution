package com.distribution.modules.dis.controller;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.dis.entity.LoanOrderInfoEntity;
import com.distribution.modules.dis.service.LoanOrderInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;


/**
 * 贷款订单
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
@Slf4j
@RestController
@RequestMapping("loanorderinfo")
public class LoanOrderInfoController {
    @Autowired
    private LoanOrderInfoService loanOrderInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("loanorderinfo:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<LoanOrderInfoEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> loanOrderInfoService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("loanorderinfo:info")
    public Result info(@PathVariable("id") String id) {
        LoanOrderInfoEntity loanOrderInfo = loanOrderInfoService.queryObject(id);

        return Result.ok().put("loanOrderInfo", loanOrderInfo);
    }
    @RequestMapping("/fenrunmoney")
    @RequiresPermissions("loanorderinfo:update")
    public Result huikuan(String applyforid, Double fenrunmoney) {
        LoanOrderInfoEntity loanOrderInfo = loanOrderInfoService.queryObject(applyforid);
        try {
            loanOrderInfo.setOrderStatus(1);
            loanOrderInfo.setLoanMoney(fenrunmoney);
            loanOrderInfo.setUpdateTime(DateUtils.formatDateTime(LocalDateTime.now()));
            loanOrderInfoService.update(loanOrderInfo);
        } catch (Exception e) {
            log.error("分润异常", e);
            return Result.error("分润异常");
        }
        return Result.ok();
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("loanorderinfo:save")
    public Result save(@RequestBody LoanOrderInfoEntity loanOrderInfo) {
        try {
            loanOrderInfo.setId(CommonUtils.getUUID());
            loanOrderInfo.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
            loanOrderInfoService.save(loanOrderInfo);
        } catch (Exception e) {
            log.error("保存贷款订单信息异常", e);
            return Result.error("保存贷款订单信息异常");
        }

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("loanorderinfo:update")
    public Result update(@RequestBody LoanOrderInfoEntity loanOrderInfo) {
        try {
            loanOrderInfo.setUpdateTime(DateUtils.formatDateTime(LocalDateTime.now()));
            loanOrderInfoService.update(loanOrderInfo);
        } catch (Exception e) {
            log.error("修改贷款订单信息异常", e);
            return Result.error("修改贷款订单信息异常");
        }
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("loanorderinfo:delete")
    public Result delete(@RequestBody String[] ids) {
        try {
            if (ids.length == 1) {
                loanOrderInfoService.delete(ids[0]);
            } else {
                loanOrderInfoService.deleteBatch(ids);
            }
        } catch (Exception e) {
            log.error("删除贷款订单信息异常", e);
            return Result.error("删除贷款订单信息异常");
        }
        return Result.ok();
    }

}
