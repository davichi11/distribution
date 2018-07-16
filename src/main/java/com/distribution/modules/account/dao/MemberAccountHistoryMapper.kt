package com.distribution.modules.account.dao


import com.distribution.modules.account.entity.MemberAccountHistory
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MemberAccountHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_histroy
     *
     * @mbg.generated
     */
    @Throws(Exception::class)
    fun deleteByPrimaryKey(id: String): Int

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_histroy
     *
     * @mbg.generated
     */
    @Throws(Exception::class)
    fun insert(record: MemberAccountHistory): Int

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_histroy
     *
     * @mbg.generated
     */
    @Throws(Exception::class)
    fun insertSelective(record: MemberAccountHistory): Int

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_histroy
     *
     * @mbg.generated
     */
    fun selectByPrimaryKey(id: String): MemberAccountHistory

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_histroy
     *
     * @mbg.generated
     */
    @Throws(Exception::class)
    fun updateByPrimaryKeySelective(record: MemberAccountHistory): Int

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_histroy
     *
     * @mbg.generated
     */
    @Throws(Exception::class)
    fun updateByPrimaryKey(record: MemberAccountHistory): Int

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<MemberAccountHistory>
}