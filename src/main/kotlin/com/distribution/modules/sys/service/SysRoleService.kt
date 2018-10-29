package com.distribution.modules.sys.service


import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.CacheUpdate
import com.alicp.jetcache.anno.Cached
import com.distribution.modules.sys.entity.SysRoleEntity
import kotlinx.coroutines.experimental.timeunit.TimeUnit


/**
 * 角色
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
interface SysRoleService {

    /**
     * 根据ID查询
     *
     * @param roleId
     * @return
     */
    @Cached(name = "SysRoleService.queryObject", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryObject(roleId: Long): SysRoleEntity

    /**
     * 列表查询
     *
     * @param map
     * @return
     */
    @Cached(name = "SysRoleService.", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryList(map: Map<String, Any>): List<SysRoleEntity>

    /**
     * 查询总记录数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 保存
     *
     * @param role
     * @throws Exception
     */
    @CacheUpdate(name = "SysMenuService.", key = "#role.id", value = "#role")
    @Throws(Exception::class)
    fun save(role: SysRoleEntity)

    /**
     * 更新
     *
     * @param role
     * @throws Exception
     */
    @CacheUpdate(name = "SysMenuService.", key = "#role.id", value = "#role")
    @Throws(Exception::class)
    fun update(role: SysRoleEntity)

    /**
     * 批量删除
     *
     * @param roleIds
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(roleIds: Array<Long>)

    /**
     * 查询用户创建的角色ID列表
     *
     * @param createUserId
     * @return
     */
    fun queryRoleIdList(createUserId: Long): List<Long>
}
