package com.distribution.modules.integral.controller

import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.Result
import com.distribution.modules.api.pojo.vo.ProductTypeVO
import com.distribution.modules.integral.entity.IntegralTutorial
import com.distribution.modules.integral.entity.ProductTypeEntity
import com.distribution.modules.integral.service.IntegralTutorialService
import com.distribution.modules.integral.service.ProductTypeService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*


/**
 * 积分兑换产品类型表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
@RestController
@RequestMapping("producttype")
class ProductTypeController {
    @Autowired
    private lateinit var productTypeService: ProductTypeService

    @Autowired
    private lateinit var integralTutorialService: IntegralTutorialService

    private val log = LoggerFactory.getLogger(ProductTypeController::class.java)

    val all: Result
        @GetMapping("/productType")
        get() = Result().ok().put("productTypes", productTypeService.queryList(mapOf()))

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("producttype:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo<ProductTypeEntity> { productTypeService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("producttype:info")
    fun info(@PathVariable("id") id: String): Result {
        val type = productTypeService.queryObject(id)
        val typeVO = ProductTypeVO()
        BeanUtils.copyProperties(type, typeVO)
        typeVO.tutorials = integralTutorialService.findByParam(mapOf("typeId" to type.id))?: listOf()
        return Result().ok().put("productType", typeVO)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("producttype:save")
    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    fun save(@RequestBody productType: ProductTypeVO): Result {
        val uuid = CommonUtils.uuid
        val typeRecord = ProductTypeEntity()
        BeanUtils.copyProperties(productType, typeRecord)
        typeRecord.id = uuid
        productTypeService.save(typeRecord)
        if (CollectionUtils.isNotEmpty(productType.tutorials)) {
            val tutorialRecords = ArrayList<IntegralTutorial>()
            productType.tutorials.forEach { tutorial ->
                val record = IntegralTutorial()
                record.step = tutorial.step
                record.textDescribe = tutorial.textDescribe
                record.imgDescribe = tutorial.imgDescribe
                record.typeId = uuid
                tutorialRecords.add(record)
            }
            integralTutorialService.batchSave(tutorialRecords)
        }
        return Result().ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("producttype:update")
    fun update(@RequestBody productType: ProductTypeVO): Result {
        try {
            val typeRecord = ProductTypeEntity()
            BeanUtils.copyProperties(productType, typeRecord)
            productTypeService.update(typeRecord)
            productType.tutorials.forEach { t ->
                val tutorialRecord = IntegralTutorial()
                BeanUtils.copyProperties(t, tutorialRecord)
                integralTutorialService.update(tutorialRecord)
            }
        } catch (e: Exception) {
            log.error("修改异常", e)
            return Result().error(msg = "修改异常")
        }

        return Result().ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("producttype:delete")
    fun delete(@RequestBody ids: Array<String>): Result {
        try {
            when {
                ids.size == 1 -> {
                    val typeEntity = productTypeService.queryObject(ids[0])
                    typeEntity.isDelete = "0"
                    productTypeService.update(typeEntity)
                }
                else -> ids.forEach {
                    val typeEntity = productTypeService.queryObject(it)
                    typeEntity.isDelete = "0"
                    productTypeService.update(typeEntity)
                }
            }
        } catch (e: Exception) {
            log.error("删除产品类型异常", e)
            return Result().error(msg = "删除产品类型异常")
        }

        return Result().ok()
    }

}
