package com.distribution.modules.account.service.impl

import com.distribution.modules.account.dao.MemberAccountHistoryMapper
import com.distribution.modules.account.entity.MemberAccountHistory
import com.distribution.modules.account.service.MemberAccountHistoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MemberAccountHistoryServiceImpl : MemberAccountHistoryService {

    @Autowired
    private lateinit var memberAccountHistoryMapper: MemberAccountHistoryMapper

    override fun findList(param: Map<String, Any>): List<MemberAccountHistory> {
        return memberAccountHistoryMapper.queryList(param)
    }
}
