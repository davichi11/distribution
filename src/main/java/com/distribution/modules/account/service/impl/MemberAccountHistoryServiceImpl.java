package com.distribution.modules.account.service.impl;

import com.distribution.modules.account.dao.MemberAccountHistoryMapper;
import com.distribution.modules.account.entity.MemberAccountHistory;
import com.distribution.modules.account.service.MemberAccountHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberAccountHistoryServiceImpl implements MemberAccountHistoryService {

    @Autowired
    private MemberAccountHistoryMapper memberAccountHistoryMapper;

    @Override
    public List<MemberAccountHistory> findList(Map<String, Object> param) {
        return memberAccountHistoryMapper.queryList(param);
    }


}
