package com.distribution.modules.integral.controller;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.pojo.vo.ProductTypeVO;
import com.distribution.modules.integral.entity.ProductTypeEntity;
import com.distribution.modules.integral.service.ProductTypeService;
import com.distribution.pojo.Tables;
import com.distribution.pojo.tables.pojos.IntegralTutorial;
import com.distribution.pojo.tables.pojos.ProductType;
import com.distribution.pojo.tables.records.IntegralTutorialRecord;
import com.distribution.pojo.tables.records.ProductTypeRecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jooq.DSLContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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
    @Autowired
    private DSLContext create;

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

    @GetMapping("/productType")
    public Result getAll() {
        return Result.ok().put("productTypes", productTypeService.queryList(Maps.newHashMap()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("producttype:info")
    public Result info(@PathVariable("id") String id) {
        ProductType type = create.selectFrom(Tables.PRODUCT_TYPE).where(Tables.PRODUCT_TYPE.ID.eq(id))
                .fetchOneInto(ProductType.class);
        ProductTypeVO typeVO = new ProductTypeVO();
        BeanUtils.copyProperties(type, typeVO);
        typeVO.setTutorials(create.selectFrom(Tables.INTEGRAL_TUTORIAL)
                .where(Tables.INTEGRAL_TUTORIAL.TYPE_ID.eq(type.getId()))
                .orderBy(Tables.INTEGRAL_TUTORIAL.STEP.asc())
                .fetchInto(IntegralTutorial.class));
        return Result.ok().put("productType", typeVO);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("producttype:save")
    @Transactional(rollbackFor = Exception.class)
    public Result save(@RequestBody ProductTypeVO productType) throws Exception {
        String uuid = CommonUtils.getUUID();
        ProductTypeRecord typeRecord = create.newRecord(Tables.PRODUCT_TYPE);
        BeanUtils.copyProperties(productType, typeRecord);
        typeRecord.setId(uuid);
        typeRecord.insert();
        if (CollectionUtils.isNotEmpty(productType.getTutorials())) {
            List<IntegralTutorialRecord> tutorialRecords = new ArrayList<>();
            productType.getTutorials().stream().filter(Objects::nonNull).forEach(tutorial -> {
                IntegralTutorialRecord record = create.newRecord(Tables.INTEGRAL_TUTORIAL);
                record.setStep(tutorial.getStep());
                record.setTextDescribe(tutorial.getTextDescribe());
                record.setImgDescribe(tutorial.getImgDescribe());
                record.setTypeId(uuid);
                tutorialRecords.add(record);
            });
            create.batchInsert(tutorialRecords).execute();
        }
        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("producttype:update")
    public Result update(@RequestBody ProductTypeVO productType) {
        try {
            ProductTypeRecord typeRecord = create.newRecord(Tables.PRODUCT_TYPE);
            BeanUtils.copyProperties(productType, typeRecord);
            typeRecord.update();
            productType.getTutorials().forEach(t -> {
                IntegralTutorialRecord tutorialRecord = create.newRecord(Tables.INTEGRAL_TUTORIAL);
                BeanUtils.copyProperties(t, tutorialRecord);
                tutorialRecord.update();
            });
        } catch (Exception e) {
            log.error("修改异常", e);
            return Result.error("修改异常");
        }
        return Result.ok();
    }

    /**
     * 禁用
     */
    @RequestMapping("/delete")
    @RequiresPermissions("producttype:delete")
    public Result delete(@RequestBody String[] ids) {
        try {
            if (ids.length == 1) {
                ProductTypeEntity productTypeEntity = productTypeService.queryObject(ids[0]);
                productTypeEntity.setIsDelete("0");
                productTypeService.update(productTypeEntity);
            } else {
                Arrays.stream(ids).forEach(id->{
                    ProductTypeEntity productTypeEntity = productTypeService.queryObject(id);
                    productTypeEntity.setIsDelete("0");
                    try {
                        productTypeService.update(productTypeEntity);
                    } catch (Exception e) {
                        log.error("禁用产品类型异常", e);
                    }
                });
            }
        } catch (Exception e) {
            log.error("禁用产品类型异常", e);
            return Result.error("禁用产品类型异常");
        }
        return Result.ok();
    }

}
