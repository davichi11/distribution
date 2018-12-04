package com.distribution.modules.integral.service.impl

import com.distribution.modules.integral.dao.ProductDetailParamsMapper
import com.distribution.modules.integral.entity.ProductDetailParams
import com.distribution.modules.integral.service.ProductDetailParamsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductDetailParamsServiceImpl : ProductDetailParamsService {
    @Autowired
    private lateinit var productDetailParamsMapper: ProductDetailParamsMapper

    /**
     * 根据ID查询
     *
     * @return
     */
    override fun queryObject(id: Int): ProductDetailParams {
        return productDetailParamsMapper.selectByPrimaryKey(id)
    }

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    override fun queryList(map: Map<String, Any>): List<ProductDetailParams>? {
        return productDetailParamsMapper.selectList(map)
    }

    override fun batchInsert(details: List<ProductDetailParams>) {
        productDetailParamsMapper.batchInsert(details)
    }

    /**
     * 保存
     *
     * @throws Exception
     */
    override fun save(productDetailParams: ProductDetailParams) {
        productDetailParamsMapper.insert(productDetailParams)
    }

    /**
     * 更新
     *
     * @throws Exception
     */
    override fun update(productDetailParams: ProductDetailParams) {
        productDetailParamsMapper.updateByPrimaryKeySelective(productDetailParams)
    }

    /**
     * 删除
     *
     * @throws Exception
     */
    override fun delete(id: Int) {
        productDetailParamsMapper.deleteByPrimaryKey(id)
    }
}