package com.distribution.modules.dis.service

import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.entity.LoanInfoEntity


/**
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */
interface LoanInfoService {
    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: String): LoanInfoEntity

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<LoanInfoEntity>

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(loanInfo: LoanInfoEntity)

    /**
     * 更新
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(loanInfo: LoanInfoEntity)

    /**
     * 删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: String)

    /**
     * 批量删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(ids: Array<String>)

    /**
     * 提交用户信息并返回办卡链接,失败返回空
     * @param member
     * @param prodId
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getProductUrl(member: DisMemberInfoEntity, prodId: String): String
}
