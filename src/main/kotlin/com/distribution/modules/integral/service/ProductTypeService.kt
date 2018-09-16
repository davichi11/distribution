package com.distribution.modules.integral.service

import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.CacheUpdate
import com.alicp.jetcache.anno.Cached
import com.distribution.modules.integral.entity.ProductTypeEntity
import java.util.concurrent.TimeUnit


/**
 * 积分兑换产品类型表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
interface ProductTypeService {
    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: String): ProductTypeEntity

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    @Cached(name = "ProductTypeService.ProductTypeList", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryList(map: Map<String, Any>): List<ProductTypeEntity>

    /**
     * 保存
     *
     * @throws Exception
     */
    @CacheUpdate(name = "ProductTypeService.ProductTypeList",key = "#productType.id",value = "#productType")
    @Throws(Exception::class)
    fun save(productType: ProductTypeEntity)

    /**
     * 更新
     *
     * @throws Exception
     */
    @CacheUpdate(name = "ProductTypeService.ProductTypeList",key = "#productType.id",value = "#productType")
    @Throws(Exception::class)
    fun update(productType: ProductTypeEntity)

    /**
     * 删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: String)

    /**
     * 批量删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(ids: Array<String>)
}
