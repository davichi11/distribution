package com.distribution.modules.sys.service


import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.CacheUpdate
import com.alicp.jetcache.anno.Cached
import com.distribution.modules.sys.entity.SysMenuEntity
import kotlinx.coroutines.experimental.timeunit.TimeUnit


/**
 * 菜单管理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年9月18日 上午9:42:16
 */
interface SysMenuService {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     * @return
     */
    @Cached(name = "SysMenuService.queryListParentId.", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryListParentId(parentId: Long?, menuIdList: List<Long>?): List<SysMenuEntity>

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     * @return
     */
    @Cached(name = "SysMenuService.queryListParentId.", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryListParentId(parentId: Long?): List<SysMenuEntity>

    /**
     * 获取不包含按钮的菜单列表
     *
     * @return
     */
    @Cached(name = "SysMenuService.queryNotButtonList.", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryNotButtonList(): MutableList<SysMenuEntity>

    /**
     * 获取用户菜单列表
     *
     * @param userId
     * @return
     */
    @Cached(name = "SysMenuService.getUserMenuList.", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun getUserMenuList(userId: Long?): List<SysMenuEntity>

    /**
     * 查询菜单
     *
     * @param menuId
     * @return
     */
    @Cached(name = "SysMenuService.queryObject.", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryObject(menuId: Long?): SysMenuEntity

    /**
     * 查询菜单列表
     *
     * @param map
     * @return
     */
    @Cached(name = "SysMenuService.", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryList(map: Map<String, Any>): List<SysMenuEntity>

    /**
     * 查询总数
     *
     * @param map
     * @return
     */
    fun queryTotal(map: Map<String, Any>): Int

    /**
     * 保存菜单
     *
     * @param menu
     * @throws Exception
     */
    @CacheUpdate(name = "SysMenuService.", key = "#menu.id", value = "#menu")
    @Throws(Exception::class)
    fun save(menu: SysMenuEntity)

    /**
     * 修改
     *
     * @param menu
     * @throws Exception
     */
    @CacheUpdate(name = "SysMenuService.", key = "#menu.id", value = "#menu")
    @Throws(Exception::class)
    fun update(menu: SysMenuEntity)

    /**
     * 删除
     *
     * @param menuIds
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(menuIds: Array<Long>)

    /**
     * 查询用户的权限列表
     *
     * @param userId
     * @return
     */
    @Cached(name = "SysMenuService.queryUserList", key = "#userId", localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryUserList(userId: Long?): List<SysMenuEntity>
}
