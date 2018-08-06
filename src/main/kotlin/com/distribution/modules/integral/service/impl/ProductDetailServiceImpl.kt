package com.distribution.modules.integral.service.impl

import com.distribution.modules.integral.dao.ProductDetailDao
import com.distribution.modules.integral.entity.ProductDetailEntity
import com.distribution.modules.integral.service.ProductDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("productDetailService")
class ProductDetailServiceImpl : ProductDetailService {
    @Autowired
    private lateinit var productDetailDao: ProductDetailDao

    override fun queryObject(id: String): ProductDetailEntity {
        return productDetailDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<ProductDetailEntity> {
        return productDetailDao.queryList(map)
    }


    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(productDetail: ProductDetailEntity) {
        productDetailDao.save(productDetail)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(productDetail: ProductDetailEntity) {
        productDetailDao.update(productDetail)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: String) {
        productDetailDao.delete(id)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        productDetailDao.deleteBatch(ids)
    }

}
