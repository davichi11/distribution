package com.distribution.modules.dis.service

import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.CacheUpdate
import com.alicp.jetcache.anno.Cached
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import kotlinx.coroutines.experimental.timeunit.TimeUnit


/**
 * 用户表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-03
 */
interface DisMemberInfoService {
    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: String): DisMemberInfoEntity?

    /**
     * 根据openID查询会员信息
     *
     * @param openId
     * @return
     */
    fun queryByOpenId(openId: String): DisMemberInfoEntity?

    /**
     * 根据手机号查询
     *
     * @param mobile
     * @return
     */
    @Cached(name = "DisMemberInfoService.", key = "#mobile", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryByMobile(mobile: String): DisMemberInfoEntity?

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<DisMemberInfoEntity>?

    fun queryByPage(map: Map<String, Any>): List<DisMemberInfoEntity>?

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(disMemberInfo: DisMemberInfoEntity)

    /**
     * 更新
     *
     * @throws Exception
     */
    @CacheUpdate(name = "DisMemberInfoService.", key = "#result.userEntity.mobile", value = "#disMemberInfo")
    @Throws(Exception::class)
    fun update(disMemberInfo: DisMemberInfoEntity)

    @Throws(Exception::class)
    fun updateDisLevel(level: Int?, type: String, id: String)

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
     * 会员升级逻辑
     *
     * @param memberInfo
     * @return
     */
    @Throws(Exception::class)
    fun levelUp(memberInfo: DisMemberInfoEntity): Boolean

    fun findByWorkerId(workerid: String): DisMemberInfoEntity?


}
