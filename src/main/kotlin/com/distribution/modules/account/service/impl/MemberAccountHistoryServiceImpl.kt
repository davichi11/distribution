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

    /**
     * 根据ID查询
     * @return
     */
    override fun queryObject(id: String): MemberAccountHistory? {
        return memberAccountHistoryMapper.selectByPrimaryKey(id)
    }

    /**
     * 查询列表
     * @param map
     * @return
     */
    override fun queryList(map: Map<String, Any>): List<MemberAccountHistory>? {
        return memberAccountHistoryMapper.queryList(map)
    }
    override fun saveHistory(history: MemberAccountHistory) {
        memberAccountHistoryMapper.insert(history)
    }

    override fun findList(param: Map<String, Any>): List<MemberAccountHistory> {
        return memberAccountHistoryMapper.queryList(param)
    }
}
