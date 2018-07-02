package com.distribution.modules.integral.controller;

import com.distribution.common.utils.Result;
import com.distribution.modules.integral.entity.IntegralOrderEntity;
import com.distribution.modules.integral.service.IntegralOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 积分兑换申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
@Slf4j
@RestController
@RequestMapping("integralorder")
public class IntegralOrderController {
    @Autowired
    private IntegralOrderService integralOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("integralorder:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<IntegralOrderEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> integralOrderService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("integralorder:info")
    public Result info(@PathVariable("id") String id) {
            IntegralOrderEntity integralOrder = integralOrderService.queryObject(id);

        return Result.ok().put("integralOrder", integralOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("integralorder:save")
    public Result save(@RequestBody IntegralOrderEntity integralOrder) {
        try {
                integralOrderService.save(integralOrder);
        } catch (Exception e) {
            log.error("保存异常", e);
            return Result.error("保存异常");
        }

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("integralorder:update")
    public Result update(@RequestBody IntegralOrderEntity integralOrder) {
        try {
                integralOrderService.update(integralOrder);
        } catch (Exception e) {
            log.error("修改异常", e);
            return Result.error("修改异常");
        }
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("integralorder:delete")
    public Result delete(@RequestBody String[]ids) {
        try {
            if (ids.length == 1) {
                    integralOrderService.delete(ids[0]);
            } else {
                    integralOrderService.deleteBatch(ids);
            }
        } catch (Exception e) {
            log.error("删除积分兑换订单异常", e);
            return Result.error("删除积分兑换订单异常");
        }
        return Result.ok();
    }

}
