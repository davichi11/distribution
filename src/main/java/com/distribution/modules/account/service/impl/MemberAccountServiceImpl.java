package com.distribution.modules.account.service.impl;

import com.distribution.modules.account.dao.MemberAccountMapper;
import com.distribution.modules.account.entity.MemberAccount;
import com.distribution.modules.account.service.MemberAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberAccountServiceImpl implements MemberAccountService {

    @Autowired
    private MemberAccountMapper memberAccountMapper;

    /**
     *
     *   根据userId查询其账户信息
     * @author liuxinxin
     * @date  13:24
     * @param
     * @return
     */
    @Override
    public MemberAccount selectMemberAccountByUserId(String userId) {
        return memberAccountMapper.selectMemberAccountByUserId(userId);
    }

    @Override
    public MemberAccount selectByAlipay(String alipayAccount) {
        return memberAccountMapper.selectByAlipay(alipayAccount);
    }
}
