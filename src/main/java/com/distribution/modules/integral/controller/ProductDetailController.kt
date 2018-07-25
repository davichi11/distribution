package com.distribution.modules.integral.controller

import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.Result
import com.distribution.modules.api.pojo.vo.ProductDetailVO
import com.distribution.modules.integral.entity.ProductDetailEntity
import com.distribution.modules.integral.service.ProductDetailService
import com.distribution.pojo.Tables
import com.distribution.pojo.tables.pojos.ProductDetailParams
import com.distribution.pojo.tables.records.ProductDetailParamsRecord
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.jooq.DSLContext
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
    private lateinit var create: DSLContext
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
        BeanUtils.copyProperties(productDetail, productDetailVO)
        productDetailVO.params = create.selectFrom(Tables.PRODUCT_DETAIL_PARAMS)
                .where(Tables.PRODUCT_DETAIL_PARAMS.DETAIL_ID.eq(id))
                .fetchInto(ProductDetailParams::class.java)
        return Result().ok().put("productDetail", productDetailVO)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("productdetail:save")
    fun save(@RequestBody detailVO: ProductDetailVO): Result {
        try {
            val productDetail = create.newRecord(Tables.PRODUCT_DETAIL)
            val id = CommonUtils.uuid
            BeanUtils.copyProperties(detailVO, productDetail)
            productDetail.id = id
            productDetail.prodDetailCount = "0"
            productDetail.insert()
            if (CollectionUtils.isNotEmpty(detailVO.params)) {
                val paramsRecords = ArrayList<ProductDetailParamsRecord>()
                detailVO.params!!.forEach { params ->
                    val paramsRecord = create.newRecord(Tables.PRODUCT_DETAIL_PARAMS)
                    BeanUtils.copyProperties(params, paramsRecord)
                    paramsRecord.detailId = id
                    paramsRecords.add(paramsRecord)
                }
                create.batchInsert(paramsRecords).execute()
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
            val detail = create.newRecord(Tables.PRODUCT_DETAIL)
            BeanUtils.copyProperties(productDetail, detail)
            detail.update()
            if (CollectionUtils.isNotEmpty(productDetail.params)) {
                productDetail.params!!.forEach { param ->
                    val paramsRecord = create.newRecord(Tables.PRODUCT_DETAIL_PARAMS)
                    BeanUtils.copyProperties(param, paramsRecord)
                    paramsRecord.update()
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
            if (ids.size == 1) {
                productDetailService.delete(ids[0])
            } else {
                productDetailService.deleteBatch(ids)
            }
        } catch (e: Exception) {
            log.error("删除积分兑换产品列表异常", e)
            return Result().error(msg = "删除积分兑换产品列表异常")
        }

        return Result().ok()
    }

}
