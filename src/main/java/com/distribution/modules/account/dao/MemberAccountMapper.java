package com.distribution.modules.account.dao;

import com.distribution.modules.account.entity.MemberAccount;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberAccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String accountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account
     *
     * @mbg.generated
     */
    int insert(MemberAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account
     *
     * @mbg.generated
     */
    int insertSelective(MemberAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account
     *
     * @mbg.generated
     */
    MemberAccount selectByPrimaryKey(String accountId);

    /**
     * 根据会员ID查询其账户信息
     * @param memberInfoEntity
     * @return
     */
    MemberAccount selectMemberAccount(String memberId);
    /**
     * 根据userId查询其账户信息
     * @param memberInfoEntity
     * @return
     */
    MemberAccount selectMemberAccountByUserId(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MemberAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MemberAccount record);
}