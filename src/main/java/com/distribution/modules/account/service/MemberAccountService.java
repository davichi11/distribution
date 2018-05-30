package com.distribution.modules.account.service;

import com.distribution.modules.account.entity.MemberAccount;

public interface MemberAccountService {

    MemberAccount selectMemberAccountByUserId(String userId);

    MemberAccount selectByAlipay(String alipayAccount);
}
