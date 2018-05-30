package com.distribution.modules.account.dao;

import com.distribution.modules.account.entity.MemberAccount;
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
    int insert(MemberAccount record) throws Exception;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account
     *
     * @mbg.generated
     */
    int insertSelective(MemberAccount record) throws Exception;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account
     *
     * @mbg.generated
     */
    MemberAccount selectByPrimaryKey(String accountId);

    /**
     * 根据会员ID查询其账户信息
     * @param memberId
     * @return
     */
    MemberAccount selectMemberAccount(String memberId);
    /**
     * 根据userId查询其账户信息
     * @param userId
     * @return
     */
    MemberAccount selectMemberAccountByUserId(String userId);

    /**
     * 根据支付宝账户查询会员账户
     * @param alipayAccount
     * @return
     */
    MemberAccount selectByAlipay(String alipayAccount);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MemberAccount record) throws Exception;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MemberAccount record) throws Exception;
}