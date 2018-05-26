package com.distribution.modules.account.service;

import com.distribution.modules.account.entity.MemberAccountHistory;

import java.util.List;
import java.util.Map;

public interface MemberAccountHistoryService {

    List<MemberAccountHistory> findList(Map<String, Object> param);
}
