package com.distribution.modules.account.service

import com.distribution.modules.account.entity.MemberAccountHistory

interface MemberAccountHistoryService {

    fun findList(param: Map<String, Any>): List<MemberAccountHistory>
}
