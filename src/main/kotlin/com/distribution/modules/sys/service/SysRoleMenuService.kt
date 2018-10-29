package com.distribution.modules.sys.service

import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.CacheUpdate
import com.alicp.jetcache.anno.Cached
import kotlinx.coroutines.experimental.timeunit.TimeUnit


/**
 * 角色与菜单对应关系
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:42:30
 */
interface SysRoleMenuService {

    /**
     * 更新或插入
     *
     * @param roleId
     * @param menuIdList
     * @throws Exception
     */
    @CacheUpdate(name = "SysRoleMenuService.", key = "#id", value = "#roleId")
    @Throws(Exception::class)
    fun saveOrUpdate(roleId: Long?, menuIdList: List<Long>)

    /**
     * 根据角色ID，获取菜单ID列表
     *
     * @param roleId
     * @return
     */
    @Cached(name = "SysRoleMenuService.", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryMenuIdList(roleId: Long?): List<Long>

}
