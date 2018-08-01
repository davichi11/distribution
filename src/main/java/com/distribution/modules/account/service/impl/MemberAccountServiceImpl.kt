package com.distribution.modules.account.service.impl

import com.distribution.modules.account.dao.MemberAccountMapper
import com.distribution.modules.account.entity.MemberAccount
import com.distribution.modules.account.service.MemberAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MemberAccountServiceImpl : MemberAccountService {



    @Autowired
    private lateinit var memberAccountMapper: MemberAccountMapper

    /**
     * 根据userId查询其账户信息
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 13:24
     */
    override fun selectMemberAccountByUserId(userId: String): MemberAccount? {
        return memberAccountMapper.selectMemberAccountByUserId(userId)
    }

    override fun selectByAlipay(alipayAccount: String): MemberAccount? {
        return memberAccountMapper.selectByAlipay(alipayAccount)
    }

    @Throws(Exception::class)
    override fun save(memberAccount: MemberAccount) {
        memberAccountMapper.insertSelective(memberAccount)
    }

    @Throws(Exception::class)
    override fun update(memberAccount: MemberAccount) {
        memberAccountMapper.updateByPrimaryKeySelective(memberAccount)
    }

    @Throws(Exception::class)
    override fun updateByUserName(name: String) {
        if (name.isNotBlank()) {
            memberAccountMapper.updateByUserName(name)
        }
    }

    override fun selectByUserName(name: String): MemberAccount? {
        return memberAccountMapper.selectByUserName(name)
    }
}
