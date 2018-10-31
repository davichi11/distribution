package com.distribution.modules.sys.service

import com.alicp.jetcache.anno.CacheInvalidate
import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.CacheUpdate
import com.alicp.jetcache.anno.Cached
import java.util.concurrent.TimeUnit


/**
 * 用户与角色对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:43:24
 */
interface SysUserRoleService {
    /**
     * 更新或保存
     *
     * @param userId
     * @param roleIdList
     * @throws Exception
     */
    @CacheUpdate(name = "SysUserRoleService.queryRoleIdList", key = "#userId", value = "#userId")
    @Throws(Exception::class)
    fun saveOrUpdate(userId: Long, roleIdList: List<Long>)

    /**
     * 根据用户ID，获取角色ID列表
     *
     * @param userId
     * @return
     */
    @Cached(name = "SysUserRoleService.queryRoleIdList", key = "#userId", localExpire = 60000, cacheType = CacheType.BOTH, expire = 6000000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryRoleIdList(userId: Long): List<Long>

    /**
     * 删除
     *
     * @param userId
     * @throws Exception
     */
    @CacheInvalidate(name = "SysUserRoleService.queryRoleIdList", key = "#userId")
    @Throws(Exception::class)
    fun delete(userId: Long)
}
