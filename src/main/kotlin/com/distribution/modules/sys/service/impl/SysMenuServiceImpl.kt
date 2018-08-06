package com.distribution.modules.sys.service.impl

import com.distribution.common.utils.Constant
import com.distribution.modules.sys.dao.SysMenuDao
import com.distribution.modules.sys.entity.SysMenuEntity
import com.distribution.modules.sys.service.SysMenuService
import com.distribution.modules.sys.service.SysUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author huchunliang
 */
@Service("sysMenuService")
class SysMenuServiceImpl : SysMenuService {
    @Autowired
    private lateinit var sysMenuDao: SysMenuDao
    @Autowired
    private lateinit var sysUserService: SysUserService

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     * @return
     */
    override fun queryListParentId(parentId: Long?, menuIdList: List<Long>?): List<SysMenuEntity> {
        val menuList = queryListParentId(parentId)
        return if (menuIdList == null) menuList else menuList.filter { menu -> menuIdList.contains(menu.menuId) }
    }

    override fun queryListParentId(parentId: Long?): List<SysMenuEntity> {
        return sysMenuDao.queryListParentId(parentId)
    }

    override fun queryNotButtonList(): MutableList<SysMenuEntity> {
        return sysMenuDao.queryNotButtonList()
    }

    override fun getUserMenuList(userId: Long?): List<SysMenuEntity> {
        //系统管理员，拥有最高权限
        if (userId!!.toInt() == Constant.SUPER_ADMIN) {
            return getAllMenuList(null)
        }

        //用户菜单列表
        val menuIdList = sysUserService.queryAllMenuId(userId)
        return getAllMenuList(menuIdList)
    }

    override fun queryObject(menuId: Long?): SysMenuEntity {
        return sysMenuDao.queryObject(menuId!!)
    }

    override fun queryList(map: Map<String, Any>): List<SysMenuEntity> {
        return sysMenuDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return sysMenuDao.queryTotal(map)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(menu: SysMenuEntity) {
        sysMenuDao.save(menu)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(menu: SysMenuEntity) {
        sysMenuDao.update(menu)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(menuIds: Array<Long>) {
        sysMenuDao.deleteBatch(menuIds)
    }

    override fun queryUserList(userId: Long?): List<SysMenuEntity> {
        return sysMenuDao.queryUserList(userId)
    }

    /**
     * 获取所有菜单列表
     */
    private fun getAllMenuList(menuIdList: List<Long>?): List<SysMenuEntity> {
        //查询根菜单列表
        val menuList = queryListParentId(0L, menuIdList)
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList)

        return menuList
    }

    /**
     * 递归
     */
    private fun getMenuTreeList(menuList: List<SysMenuEntity>, menuIdList: List<Long>?): List<SysMenuEntity> {
        val subMenuList = mutableListOf<SysMenuEntity>()

        //目录
        menuList.forEach { entity ->
            //目录
            if (entity.type == Constant.MenuType.CATALOG.value) {
                entity.list = getMenuTreeList(queryListParentId(entity.menuId, menuIdList), menuIdList)
            }
            subMenuList.add(entity)
        }

        return subMenuList
    }
}
