package com.distribution.modules.dis.service

import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.CacheUpdate
import com.alicp.jetcache.anno.Cached
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.entity.LoanInfoEntity
import java.util.concurrent.TimeUnit


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
    @Cached(name = "LoanInfoService.", key = "#id", localExpire = 60000, cacheType = CacheType.BOTH, expire = 6000000, timeUnit = TimeUnit.MILLISECONDS)
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
    @CacheUpdate(name = "LoanInfoService.", key = "#loanInfo.id", value = "#loanInfo")
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
