package com.distribution.modules.integral.controller;

import com.distribution.common.utils.Result;
import com.distribution.modules.integral.entity.IntegralOrderEntity;
import com.distribution.modules.integral.service.IntegralOrderService;
import com.distribution.modules.integral.service.ProductDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
    @Autowired
    private ProductDetailService productDetailService;

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
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("integralorder:delete")
    public Result delete(@RequestBody String[] ids) {
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

    /**
     * 审核
     *
     * @param id
     * @param status
     * @return
     */
    @PatchMapping("/{id}/{status}/{money}")
    public Result approve(@PathVariable("id") String id, @PathVariable("status") String status,
                          @PathVariable("money") Double money) {
        IntegralOrderEntity entity = integralOrderService.queryObject(id);
        entity.setStatus(NumberUtils.toInt(status));
        try {
            integralOrderService.update(entity, money);
            return Result.ok("审核成功");
        } catch (Exception e) {
            log.error("审核异常", e);
            return Result.error("审核异常");
        }
    }

}
