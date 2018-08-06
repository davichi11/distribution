package com.distribution.modules.integral.service.impl

import com.distribution.modules.integral.dao.ProductTypeDao
import com.distribution.modules.integral.entity.ProductTypeEntity
import com.distribution.modules.integral.service.ProductTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("productTypeService")
class ProductTypeServiceImpl : ProductTypeService {
    @Autowired
    private lateinit var productTypeDao: ProductTypeDao

    override fun queryObject(id: String): ProductTypeEntity {
        return productTypeDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<ProductTypeEntity> {
        return productTypeDao.queryList(map)
    }


    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(productType: ProductTypeEntity) {
        productTypeDao.save(productType)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(productType: ProductTypeEntity) {
        productTypeDao.update(productType)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: String) {
        productTypeDao.delete(id)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        productTypeDao.deleteBatch(ids)
    }

}
