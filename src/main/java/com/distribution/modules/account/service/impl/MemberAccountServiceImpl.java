package com.distribution.modules.account.service.impl;

import com.distribution.modules.account.dao.MemberAccountMapper;
import com.distribution.modules.account.entity.MemberAccount;
import com.distribution.modules.account.service.MemberAccountService;
<<<<<<< HEAD
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
=======
>>>>>>> origin/develop
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberAccountServiceImpl implements MemberAccountService {

    @Autowired
    private MemberAccountMapper memberAccountMapper;

    /**
<<<<<<< HEAD
     *
     *   根据userId查询其账户信息
     * @author liuxinxin
     * @date  13:24
     * @param
     * @return
=======
     * 根据userId查询其账户信息
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 13:24
>>>>>>> origin/develop
     */
    @Override
    public MemberAccount selectMemberAccountByUserId(String userId) {
        return memberAccountMapper.selectMemberAccountByUserId(userId);
    }
<<<<<<< HEAD
=======

    @Override
    public MemberAccount selectByAlipay(String alipayAccount) {
        return memberAccountMapper.selectByAlipay(alipayAccount);
    }

    @Override
    public void save(MemberAccount memberAccount) throws Exception {
        memberAccountMapper.insertSelective(memberAccount);
    }

    @Override
    public void update(MemberAccount memberAccount) throws Exception {
        memberAccountMapper.updateByPrimaryKeySelective(memberAccount);
    }
>>>>>>> origin/develop
}
