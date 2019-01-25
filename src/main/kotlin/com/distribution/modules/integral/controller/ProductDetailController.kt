package com.distribution.modules.integral.controller

import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.Result
import com.distribution.modules.api.pojo.vo.ProductDetailVO
import com.distribution.modules.integral.entity.ProductDetailEntity
import com.distribution.modules.integral.entity.ProductDetailParams
import com.distribution.modules.integral.service.ProductDetailParamsService
import com.distribution.modules.integral.service.ProductDetailService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*


/**
 * 积分兑换产品列表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
@RestController
@RequestMapping("productdetail")
class ProductDetailController {
    @Autowired
    private lateinit var productDetailService: ProductDetailService
    @Autowired
    private lateinit var productDetailParamsService: ProductDetailParamsService
    private val log = LoggerFactory.getLogger(ProductDetailController::class.java)

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("productdetail:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo<ProductDetailEntity> { productDetailService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("productdetail:info")
    fun info(@PathVariable("id") id: String): Result {
        val productDetail = productDetailService.queryObject(id)
        val productDetailVO = ProductDetailVO()
        val list = productDetailParamsService.queryList(mapOf("detailId" to productDetail.id))?: listOf()
        BeanUtils.copyProperties(productDetail, productDetailVO)
        productDetailVO.params = list
        return Result().ok().put("productDetail", productDetailVO)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("productdetail:save")
    fun save(@RequestBody detailVO: ProductDetailVO): Result {
        try {
            val productDetail = ProductDetailEntity()
            val id = CommonUtils.uuid
            BeanUtils.copyProperties(detailVO, productDetail)
            productDetail.id = id
            productDetail.prodDetailCount = "0"
            productDetailService.save(productDetail)
            if (CollectionUtils.isNotEmpty(detailVO.params)) {
                val paramsRecords = ArrayList<ProductDetailParams>()
                detailVO.params.forEach { params ->
                    val paramsRecord = ProductDetailParams()
                    BeanUtils.copyProperties(params, paramsRecord)
                    paramsRecord.detailId = id
                    paramsRecords.add(paramsRecord)
                }
                productDetailParamsService.batchInsert(paramsRecords)
            }
        } catch (e: Exception) {
            log.error("保存积分兑换产品列表异常", e)
            return Result().error(msg = "保存积分兑换产品列表异常")
        }

        return Result().ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("productdetail:update")
    fun update(@RequestBody productDetail: ProductDetailVO): Result {
        try {
            val detail = ProductDetailEntity()
            BeanUtils.copyProperties(productDetail, detail)
            productDetailService.update(detail)
            if (CollectionUtils.isNotEmpty(productDetail.params)) {
                productDetail.params.forEach { param ->
                    val paramsRecord = ProductDetailParams()
                    BeanUtils.copyProperties(param, paramsRecord)
                    productDetailParamsService.update(paramsRecord)
                }
            }
        } catch (e: Exception) {
            log.error("修改积分兑换产品列表异常", e)
            return Result().error(msg = "修改积分兑换产品列表异常")
        }

        return Result().ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("productdetail:delete")
    fun delete(@RequestBody ids: Array<String>): Result {
        try {
            when {
                ids.size == 1 -> {
                    val productDetail = productDetailService.queryObject(ids[0])
                    productDetail.isDelete = "0"
                    productDetailService.update(productDetail)
                }
                else -> ids.forEach {
                    val productDetail = productDetailService.queryObject(it)
                    productDetail.isDelete = "0"
                    productDetailService.update(productDetail)
                }
            }
        } catch (e: Exception) {
            log.error("删除积分兑换产品列表异常", e)
            return Result().error(msg = "删除积分兑换产品列表异常")
        }

        return Result().ok()
    }

}
