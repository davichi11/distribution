package com.distribution.modules.api.controller;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.pojo.vo.ProductDetailVO;
import com.distribution.modules.api.pojo.vo.ProductTypeVO;
import com.distribution.modules.integral.entity.IntegralOrderEntity;
import com.distribution.modules.integral.service.IntegralOrderService;
import com.distribution.modules.integral.service.ProductDetailService;
import com.distribution.modules.integral.service.ProductTypeService;
import com.distribution.pojo.Tables;
import com.distribution.pojo.tables.pojos.*;
import com.distribution.pojo.tables.records.IntegralOrderRecord;
import com.distribution.pojo.tables.records.ProductDetailRecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.impl.factory.Maps;
import org.jooq.DSLContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.distribution.pojo.Tables.*;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @Description TODO(描述)
 * @create 2018/7/1-20:57
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api("积分兑换接口")
@CrossOrigin
public class ApiIntegralController {
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private IntegralOrderService integralOrderService;
    @Autowired
    private DSLContext create;

    @AuthIgnore
    @GetMapping("/productType")
    @ApiOperation("查询所有产品类型")
    public Result getAllProductType() {
        List<ProductTypeVO> productTypeVOS = productTypeService.queryList(Maps.mutable.empty()).stream().map(type -> {
            ProductTypeVO typeVO = new ProductTypeVO();
            BeanUtils.copyProperties(type, typeVO);
            typeVO.setTutorials(create.selectFrom(Tables.INTEGRAL_TUTORIAL)
                    .where(Tables.INTEGRAL_TUTORIAL.TYPE_ID.eq(type.getId()))
                    .orderBy(Tables.INTEGRAL_TUTORIAL.STEP.asc())
                    .fetchInto(IntegralTutorial.class));
            return typeVO;
        }).collect(Collectors.toList());
        return Result.ok().put("productTypes", productTypeVOS);
    }

    @AuthIgnore
    @GetMapping("/productDetail/{type}")
    @ApiOperation("根据产品类型查询产品")
    public Result getProductDetailByType(@PathVariable("type") String type) {
        List<ProductDetail> productDetails = create.selectFrom(PRODUCT_DETAIL)
                .where(PRODUCT_DETAIL.PROD_TYPE_ID.eq(type))
                .orderBy(PRODUCT_DETAIL.PROD_DETAIL_VALUE.asc())
                .fetchInto(ProductDetail.class);
        List<ProductDetailVO> detailVOS = productDetails.stream().map(productDetail -> {
            ProductDetailVO detailVO = new ProductDetailVO();
            BeanUtils.copyProperties(productDetail, detailVO);
            detailVO.setParams(create.selectFrom(PRODUCT_DETAIL_PARAMS)
                    .where(PRODUCT_DETAIL_PARAMS.DETAIL_ID.eq(productDetail.getId()))
                    .fetchInto(ProductDetailParams.class));
            return detailVO;
        }).collect(Collectors.toList());
        return Result.ok().put("productDetails", detailVOS);
    }


    @AuthIgnore
    @GetMapping("/productType/{detailId}")
    @ApiOperation("根据产品查询产品类型")
    public Result getProductTypeByDetail(@PathVariable("detailId") String detailId) {
        ProductDetailRecord detailRecord = create.selectFrom(Tables.PRODUCT_DETAIL)
                .where(Tables.PRODUCT_DETAIL.ID.eq(detailId)).fetchOne();
        ProductType type = create.selectFrom(Tables.PRODUCT_TYPE)
                .where(Tables.PRODUCT_TYPE.ID.eq(detailRecord.getProdTypeId())).fetchOneInto(ProductType.class);
        return Result.ok().put("productType", type);
    }

    @AuthIgnore
    @GetMapping("/tutorial/{type}")
    @ApiOperation("根据产品类型查询图文教程")
    public Result getIntegralTutorials(@PathVariable("type") String type) {
        List<IntegralTutorial> integralTutorials = create.selectFrom(Tables.INTEGRAL_TUTORIAL)
                .where(Tables.INTEGRAL_TUTORIAL.TYPE_ID.eq(type))
                .orderBy(Tables.INTEGRAL_TUTORIAL.STEP.asc())
                .fetchInto(IntegralTutorial.class);
        return Result.ok().put("tutorials", integralTutorials);
    }


    @GetMapping("/integralOrder/{mobile}")
    @ApiOperation("查询用户的兑换记录")
    public Result getIntegralOrders(@PathVariable("mobile") String mobile, Integer page, Integer limit, Integer status) {
        Map<String, Object> param = new HashMap<>();
        param.put("mobile", mobile);
        param.put("status", status);
        //查询列表数据
        PageInfo<IntegralOrderEntity> pageInfo = PageHelper.startPage(page, limit)
                .doSelectPageInfo(() -> integralOrderService.queryList(param));
        return Result.ok().put("integralOrders", pageInfo);
    }

    @PostMapping("/integralOrder")
    @ApiOperation("提交用户积分兑换申请")
    @ApiImplicitParam(paramType = "body", dataType = "IntegralOrder", name = "integralOrder", value = "积分兑换申请", required = true)
    public Result submitIntegralOrder(@RequestBody IntegralOrder integralOrder) {
        IntegralOrderRecord record = create.newRecord(INTEGRAL_ORDER);
        BeanUtils.copyProperties(integralOrder, record);
        record.setId(CommonUtils.getUUID());
        record.setStatus("2");
        record.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
        int i;
        try {
            i = record.insert();
        } catch (Exception e) {
            log.error("提交异常", e);
            return Result.error("提交异常");
        }
        return i > 0 ? Result.ok("提交成功") : Result.error("提交失败");
    }
}
