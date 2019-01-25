package com.distribution.modules.integral.dao

import com.distribution.modules.integral.entity.IntegralTutorial
import org.apache.ibatis.annotations.Mapper

/**
 * Created by Mybatis Generator 2018/12/04
 */
@Mapper
interface IntegralTutorialMapper {
    fun deleteByPrimaryKey(id: Int): Int

    fun insert(record: IntegralTutorial): Int

    fun insertSelective(record: IntegralTutorial): Int

    fun selectByPrimaryKey(id: Int): IntegralTutorial?

    fun selectList(param:Map<String,Any>): List<IntegralTutorial>?

    fun updateByPrimaryKeySelective(record: IntegralTutorial): Int

    fun updateByPrimaryKey(record: IntegralTutorial): Int

    fun batchInsert(integralTutorials: List<IntegralTutorial>)

}