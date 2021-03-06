package com.distribution.modules.account.service

import com.distribution.modules.account.entity.MemberAccountHistory

interface MemberAccountHistoryService {

    fun findList(param: Map<String, Any>): List<MemberAccountHistory>

    fun saveHistory(history: MemberAccountHistory)
    /**
     * 根据ID查询
     * @return
     */
    fun queryObject(id: String): MemberAccountHistory?

    /**
     * 查询列表
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<MemberAccountHistory>?
}
