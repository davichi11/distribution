package com.distribution.modules.account.service;

import com.distribution.modules.account.entity.MemberAccount;

public interface MemberAccountService {

    MemberAccount selectMemberAccountByUserId(String userId);
<<<<<<< HEAD
=======

    /**
     * 根据支付宝账户查询
     * @param alipayAccount
     * @return
     */
    MemberAccount selectByAlipay(String alipayAccount);

    /**
     * 保存用户账户信息
     * @param memberAccount
     * @throws Exception
     */
    void save(MemberAccount memberAccount) throws Exception;

    /**
     * 跟下账户信息
     * @param memberAccount
     * @throws Exception
     */
    void update(MemberAccount memberAccount) throws Exception;
>>>>>>> origin/develop
}
