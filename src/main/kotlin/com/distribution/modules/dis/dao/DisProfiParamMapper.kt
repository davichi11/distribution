package com.distribution.modules.dis.dao

import com.distribution.modules.dis.entity.DisProfiParam
import org.apache.ibatis.annotations.Mapper

@Mapper
interface DisProfiParamMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profi_param
     *
     * @mbg.generated
     */
    fun deleteByPrimaryKey(id: String): Int

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profi_param
     *
     * @mbg.generated
     */
    fun insert(record: DisProfiParam): Int

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profi_param
     *
     * @mbg.generated
     */
    fun insertSelective(record: DisProfiParam): Int

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profi_param
     *
     * @mbg.generated
     */
    fun selectByPrimaryKey(id: String): DisProfiParam

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profi_param
     *
     * @mbg.generated
     */
    fun updateByPrimaryKeySelective(record: DisProfiParam): Int

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dis_profi_param
     *
     * @mbg.generated
     */
    fun updateByPrimaryKey(record: DisProfiParam): Int

    /**
     * 批量删除
     *
     * @param ids
     */
    fun deleteBatch(ids: Array<String>)

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<DisProfiParam>
}