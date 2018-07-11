package com.distribution.modules.integral.controller;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.pojo.vo.ProductDetailVO;
import com.distribution.modules.integral.entity.ProductDetailEntity;
import com.distribution.modules.integral.service.ProductDetailService;
import com.distribution.pojo.Tables;
import com.distribution.pojo.tables.pojos.ProductDetailParams;
import com.distribution.pojo.tables.records.ProductDetailParamsRecord;
import com.distribution.pojo.tables.records.ProductDetailRecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jooq.DSLContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private DSLContext create;

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
        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtils.copyProperties(productDetail, productDetailVO);
        productDetailVO.setParams(create.selectFrom(Tables.PRODUCT_DETAIL_PARAMS)
                .where(Tables.PRODUCT_DETAIL_PARAMS.DETAIL_ID.eq(id))
                .fetchInto(ProductDetailParams.class));
        return Result.ok().put("productDetail", productDetailVO);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("productdetail:save")
    public Result save(@RequestBody ProductDetailVO detailVO) {
        try {
            ProductDetailRecord productDetail = create.newRecord(Tables.PRODUCT_DETAIL);
            String id = CommonUtils.getUUID();
            BeanUtils.copyProperties(detailVO, productDetail);
            productDetail.setId(id);
            productDetail.setProdDetailCount(0);
            productDetail.insert();
            if (CollectionUtils.isNotEmpty(detailVO.getParams())) {
                List<ProductDetailParamsRecord> paramsRecords = new ArrayList<>();
                detailVO.getParams().forEach(params -> {
                    ProductDetailParamsRecord paramsRecord = create.newRecord(Tables.PRODUCT_DETAIL_PARAMS);
                    BeanUtils.copyProperties(params, paramsRecord);
                    paramsRecord.setDetailId(id);
                    paramsRecords.add(paramsRecord);
                });
                create.batchInsert(paramsRecords).execute();
            }
        } catch (Exception e) {
            log.error("保存积分兑换产品列表异常", e);
            return Result.error("保存积分兑换产品列表异常");
        }

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("productdetail:update")
    public Result update(@RequestBody ProductDetailVO productDetail) {
        try {
            ProductDetailRecord detail = create.newRecord(Tables.PRODUCT_DETAIL);
            BeanUtils.copyProperties(productDetail, detail);
            if (CollectionUtils.isNotEmpty(productDetail.getParams())) {
                productDetail.getParams().forEach(param -> {
                    ProductDetailParamsRecord paramsRecord = create.newRecord(Tables.PRODUCT_DETAIL_PARAMS);
                    BeanUtils.copyProperties(param, paramsRecord);
                    paramsRecord.update();
                });
            }
        } catch (Exception e) {
            log.error("修改积分兑换产品列表异常", e);
            return Result.error("修改积分兑换产品列表异常");
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
            log.error("删除积分兑换产品列表异常", e);
            return Result.error("删除积分兑换产品列表异常");
        }
        return Result.ok();
    }

}
