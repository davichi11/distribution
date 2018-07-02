package com.distribution.modules.integral.controller;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.integral.entity.ProductTypeEntity;
import com.distribution.modules.integral.service.ProductTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 积分兑换产品类型表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
@Slf4j
@RestController
@RequestMapping("producttype")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("producttype:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<ProductTypeEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> productTypeService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("producttype:info")
    public Result info(@PathVariable("id") String id) {
        ProductTypeEntity productType = productTypeService.queryObject(id);

        return Result.ok().put("productType", productType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("producttype:save")
    public Result save(@RequestBody ProductTypeEntity productType) {
        try {
            productType.setId(CommonUtils.getUUID());
            productTypeService.save(productType);
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
    @RequiresPermissions("producttype:update")
    public Result update(@RequestBody ProductTypeEntity productType) {
        try {
            productTypeService.update(productType);
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
    @RequiresPermissions("producttype:delete")
    public Result delete(@RequestBody String[] ids) {
        try {
            if (ids.length == 1) {
                productTypeService.delete(ids[0]);
            } else {
                productTypeService.deleteBatch(ids);
            }
        } catch (Exception e) {
            log.error("删除产品类型异常", e);
            return Result.error("删除产品类型异常");
        }
        return Result.ok();
    }

}
