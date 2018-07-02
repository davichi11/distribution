package com.distribution.modules.integral.controller;

import com.distribution.common.utils.Result;
import com.distribution.modules.integral.entity.ProductDetailEntity;
import com.distribution.modules.integral.service.ProductDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 积分兑换产品列表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
@Slf4j
@RestController
@RequestMapping("productdetail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("productdetail:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<ProductDetailEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> productDetailService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("productdetail:info")
    public Result info(@PathVariable("id") String id) {
        ProductDetailEntity productDetail = productDetailService.queryObject(id);

        return Result.ok().put("productDetail", productDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("productdetail:save")
    public Result save(@RequestBody ProductDetailEntity productDetail) {
        try {
            productDetailService.save(productDetail);
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
    @RequiresPermissions("productdetail:update")
    public Result update(@RequestBody ProductDetailEntity productDetail) {
        try {
            productDetailService.update(productDetail);
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
    @RequiresPermissions("productdetail:delete")
    public Result delete(@RequestBody String[] ids) {
        try {
            if (ids.length == 1) {
                productDetailService.delete(ids[0]);
            } else {
                productDetailService.deleteBatch(ids);
            }
        } catch (Exception e) {
            log.error("删除产品详情异常", e);
            return Result.error("删除产品详情异常");
        }
        return Result.ok();
    }

}
