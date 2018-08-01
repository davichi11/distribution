package com.distribution.modules.account.service

import com.distribution.modules.account.entity.MemberAccount
import org.apache.ibatis.annotations.Param

interface MemberAccountService {

    fun selectMemberAccountByUserId(userId: String): MemberAccount?

    /**
     * 根据支付宝账户查询
     * @param alipayAccount
     * @return
     */
    fun selectByAlipay(alipayAccount: String): MemberAccount?

    fun selectByUserName(@Param("name") name: String): MemberAccount?

    /**
     * 保存用户账户信息
     * @param memberAccount
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(memberAccount: MemberAccount)

    /**
     * 跟下账户信息
     * @param memberAccount
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(memberAccount: MemberAccount)

    /**
     * 根据会员姓名更新
     */
    @Throws(Exception::class)
    fun updateByUserName(name: String)
}
