package com.distribution.modules.api.controller.product

import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.api.pojo.vo.ProductDetailVO
import com.distribution.modules.api.pojo.vo.ProductTypeVO
import com.distribution.modules.dis.service.DisMemberInfoService
import com.distribution.modules.integral.entity.IntegralOrderEntity
import com.distribution.modules.integral.entity.ProductDetailParams
import com.distribution.modules.integral.service.*
import com.github.pagehelper.PageHelper
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @Description TODO(描述)
 * @create 2018/7/1-20:57
 */

@RestController
@RequestMapping("/api")
@Api("积分兑换接口")
@CrossOrigin
class ApiIntegralController {
    @Autowired
    private lateinit var disMemberInfoService: DisMemberInfoService
    @Autowired
    private lateinit var productDetailService: ProductDetailService
    @Autowired
    private lateinit var productDetailParamsService: ProductDetailParamsService
    @Autowired
    private lateinit var productTypeService: ProductTypeService
    @Autowired
    private lateinit var integralOrderService: IntegralOrderService
    @Autowired
    private lateinit var integralTutorialService: IntegralTutorialService
    private val log = LoggerFactory.getLogger(ApiIntegralController::class.java)

    val allProductType: Result
        @AuthIgnore
        @GetMapping("/productType")
        @ApiOperation("查询所有产品类型")
        get() {
            val productTypeVOS = productTypeService.queryList(mapOf()).map { type ->
                val typeVO = ProductTypeVO()
                BeanUtils.copyProperties(type, typeVO)
                typeVO.tutorials = integralTutorialService.findByParam(mapOf("typeId" to type.id)) ?: listOf()
                typeVO
            }.toList()
            return Result().ok().put("productTypes", productTypeVOS)
        }

    @AuthIgnore
    @GetMapping("/productDetail/{type}")
    @ApiOperation("根据产品类型查询产品")
    fun getProductDetailByType(@PathVariable("type") type: String): Result {
        val productDetails = productDetailService.queryList(mapOf("type" to type))
        val detailVOS = productDetails.map { productDetail ->
            val detailVO = ProductDetailVO()
            BeanUtils.copyProperties(productDetail, detailVO)
            detailVO.params = productDetailParamsService.queryList(mapOf("detailId" to productDetail.id)) ?: listOf<ProductDetailParams>()
            detailVO
        }.toList()
        return Result().ok().put("productDetails", detailVOS)
    }


    @AuthIgnore
    @GetMapping("/productType/{detailId}")
    @ApiOperation("根据产品查询产品类型")
    fun getProductTypeByDetail(@PathVariable("detailId") detailId: String): Result {
        val detailRecord = productDetailService.queryObject(detailId)
        val type = productTypeService.queryObject(detailRecord.prodTypeId)
        return Result().ok().put("productType", type)
    }

    @AuthIgnore
    @GetMapping("/tutorial/{type}")
    @ApiOperation("根据产品类型查询图文教程")
    fun getIntegralTutorials(@PathVariable("type") type: String): Result {
        val integralTutorials = integralTutorialService.findByParam(mapOf("typeId" to type)) ?: listOf()
        return Result().ok().put("tutorials", integralTutorials)
    }


    @GetMapping("/integralOrder/{mobile}")
    @ApiOperation("查询用户的兑换记录")
    fun getIntegralOrders(@PathVariable("mobile") mobile: String, page: Int = 0, limit: Int = 0, status: Int = 0): Result {
        val member = disMemberInfoService.queryByMobile(mobile)!!
        val memberIds = member.disMemberChildren!!.map { it.id }.toMutableList()
        memberIds.add(member.id)
        val param = mapOf("memberIds" to memberIds, "status" to status)
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(page, limit)
                .doSelectPageInfo<IntegralOrderEntity> { integralOrderService.queryList(param) }
        return Result().ok().put("integralOrders", pageInfo)
    }

    @PostMapping("/integralOrder")
    @ApiOperation("提交用户积分兑换申请")
    @ApiImplicitParam(paramType = "body", dataType = "IntegralOrder", name = "integralOrder", value = "积分兑换申请", required = true)
    fun submitIntegralOrder(@RequestBody integralOrder: IntegralOrderEntity): Result {
        val record = IntegralOrderEntity()
        BeanUtils.copyProperties(integralOrder, record)
        record.id = CommonUtils.uuid
        record.status = 2
        record.addTime = DateUtils.formatDateTime(LocalDateTime.now())
        return try {
            integralOrderService.save(record)
            Result().ok("提交成功")
        } catch (e: Exception) {
            log.error("提交异常", e)
            Result().error(msg = "提交异常")
        }

    }
}
