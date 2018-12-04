package com.distribution.modules.integral.dao

import com.distribution.modules.integral.entity.ProductDetailParams
import org.apache.ibatis.annotations.Mapper

/**
 * Created by Mybatis Generator 2018/12/04
 */
@Mapper
interface ProductDetailParamsMapper {
    fun deleteByPrimaryKey(id: Int?): Int

    fun insert(record: ProductDetailParams): Int

    fun insertSelective(record: ProductDetailParams): Int

    fun selectByPrimaryKey(id: Int?): ProductDetailParams

    fun selectList(param: Map<String, Any>): List<ProductDetailParams>?

    fun updateByPrimaryKeySelective(record: ProductDetailParams): Int

    fun updateByPrimaryKey(record: ProductDetailParams): Int

    fun batchInsert(details: List<ProductDetailParams>)
}