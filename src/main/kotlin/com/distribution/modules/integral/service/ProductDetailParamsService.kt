package com.distribution.modules.integral.service

import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.CacheUpdate
import com.alicp.jetcache.anno.Cached
import com.distribution.modules.integral.entity.ProductDetailParams
import java.util.concurrent.TimeUnit

/**
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.integral.service
 * @Description TODO(描述)
 * @author ChunLiang Hu
 * @create 2018-12-04-20:07
 */
interface ProductDetailParamsService {
    /**
     * 根据ID查询
     *
     * @return
     */
    @Cached(name = "ProductDetailParamsService.",key = "#id", localExpire = 60000, cacheType = CacheType.BOTH, expire = 6000000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryObject(id: Int): ProductDetailParams

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<ProductDetailParams>?

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(productDetailParams: ProductDetailParams)

    /**
     * 更新
     *
     * @throws Exception
     */
    @CacheUpdate(name = "ProductDetailParamsService.",key = "#productDetailParams.id",value = "#productDetailParams")
    @Throws(Exception::class)
    fun update(productDetailParams: ProductDetailParams)

    /**
     * 删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: Int)

    fun batchInsert(details: List<ProductDetailParams>)
}