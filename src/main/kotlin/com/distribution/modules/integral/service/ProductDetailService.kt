package com.distribution.modules.integral.service

import com.distribution.modules.integral.entity.ProductDetailEntity


/**
 * 积分兑换产品列表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
interface ProductDetailService {
    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: String): ProductDetailEntity

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<ProductDetailEntity>

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(productDetail: ProductDetailEntity)

    /**
     * 更新
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(productDetail: ProductDetailEntity)

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
