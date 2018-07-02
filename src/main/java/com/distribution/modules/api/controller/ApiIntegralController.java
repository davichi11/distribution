package com.distribution.modules.api.controller;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.integral.service.IntegralOrderService;
import com.distribution.modules.integral.service.ProductDetailService;
import com.distribution.modules.integral.service.ProductTypeService;
import com.distribution.pojo.tables.pojos.IntegralOrder;
import com.distribution.pojo.tables.pojos.ProductDetail;
import com.distribution.pojo.tables.records.IntegralOrderRecord;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.DSLContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.distribution.pojo.Tables.INTEGRAL_ORDER;
import static com.distribution.pojo.Tables.PRODUCT_DETAIL;

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
        return Result.ok().put("productTypes", productDetailService.queryList(Maps.newHashMap()));
    }

    @AuthIgnore
    @GetMapping("/productDetail/{type}")
    @ApiOperation("根据产品类型查询产品")
    public Result getProductDetailByType(@PathVariable("type") String type) {
        List<ProductDetail> productDetails = create.selectFrom(PRODUCT_DETAIL)
                .where(PRODUCT_DETAIL.PROD_TYPE_ID.eq(type)).fetchInto(ProductDetail.class);
        return Result.ok().put("productDetails", productDetails);
    }


    @GetMapping("/integralOrder/{mobile}")
    @ApiOperation("查询用户的兑换记录")
    public Result getIntegralOrders(@PathVariable("mobile") String mobile) {
        List<IntegralOrder> integralOrders = create.selectFrom(INTEGRAL_ORDER)
                .where(INTEGRAL_ORDER.MOBILE.eq(NumberUtils.toLong(mobile))).fetchInto(IntegralOrder.class);
        return Result.ok().put("integralOrders", integralOrders);
    }

    @PostMapping("/integralOrder")
    @ApiOperation("提交用户积分兑换申请")
    @ApiImplicitParam(paramType = "body",dataType = "IntegralOrder", name = "integralOrder", value = "积分兑换申请", required = true)
    public Result subimtIntegralOrder(@RequestBody IntegralOrder integralOrder) {
        IntegralOrderRecord record = create.newRecord(INTEGRAL_ORDER);
        BeanUtils.copyProperties(integralOrder, record);
        record.setId(CommonUtils.getUUID());
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
