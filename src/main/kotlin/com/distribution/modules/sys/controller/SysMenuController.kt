package com.distribution.modules.sys.controller

import com.distribution.common.annotation.SysLog
import com.distribution.common.exception.RRException
import com.distribution.common.utils.Constant
import com.distribution.common.utils.Constant.MenuType
import com.distribution.common.utils.Result
import com.distribution.modules.sys.entity.SysMenuEntity
import com.distribution.modules.sys.service.ShiroService
import com.distribution.modules.sys.service.SysMenuService
import org.apache.commons.lang.StringUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 系统菜单
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/menu")
class SysMenuController : AbstractController() {
    @Autowired
    private lateinit var sysMenuService: SysMenuService
    @Autowired
    private lateinit var shiroService: ShiroService

    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    fun list(): List<SysMenuEntity> {
        return sysMenuService.queryList(mapOf())
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    fun select(): Result {
        //查询列表数据
        val menuList = sysMenuService.queryNotButtonList()

        //添加顶级菜单
        val root = SysMenuEntity()
        root.menuId = 0L
        root.name = "一级菜单"
        root.parentId = -1L
        root.open = true
        menuList.add(root)

        return Result().ok().put("menuList", menuList)
    }

    /**
     * 角色授权菜单
     */
    @RequestMapping("/perms")
    @RequiresPermissions("sys:menu:perms")
    fun perms(): Result {
        //查询列表数据
        val menuList: List<SysMenuEntity> = if (userId!!.toInt() == Constant.SUPER_ADMIN) {
            sysMenuService.queryList(mapOf())
        } else {
            sysMenuService.queryUserList(userId)
        }

        //只有超级管理员，才能查看所有管理员列表

        return Result().ok().put("menuList", menuList)
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    fun info(@PathVariable("menuId") menuId: Long?): Result {
        val menu = sysMenuService.queryObject(menuId)
        return Result().ok().put("menu", menu)
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    fun save(@RequestBody menu: SysMenuEntity): Result {
        //数据校验
        verifyForm(menu)

        try {
            sysMenuService.save(menu)
        } catch (e: Exception) {
            logger.error("保存菜单异常", e)
            return Result().error(msg = "保存菜单异常")
        }

        return Result().ok()
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    fun update(@RequestBody menu: SysMenuEntity): Result {
        //数据校验
        verifyForm(menu)

        try {
            sysMenuService.update(menu)
        } catch (e: Exception) {
            logger.error("更新菜单异常", e)
            return Result().error(msg = "更新菜单异常")
        }

        return Result().ok()
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    fun delete(menuId: Long): Result {
        if (menuId <= 30) {
            return Result().error(msg = "系统菜单，不能删除")
        }

        //判断是否有子菜单或按钮
        val menuList = sysMenuService.queryListParentId(menuId)
        if (menuList.isNotEmpty()) {
            return Result().error(msg = "请先删除子菜单或按钮")
        }

        try {
            sysMenuService.deleteBatch(arrayOf(menuId))
        } catch (e: Exception) {
            logger.error("删除菜单异常", e)
            return Result().error(msg = "删除菜单异常")
        }

        return Result().ok()
    }

    /**
     * 用户菜单列表
     */
    @RequestMapping("/user")
    fun user(): Result {
        val menuList = sysMenuService.getUserMenuList(userId)
        val permissions = shiroService.getUserPermissions(userId!!)
        return Result().ok().put("menuList", menuList).put("permissions", permissions)
    }

    /**
     * 验证参数是否正确
     */
    private fun verifyForm(menu: SysMenuEntity) {
        if (StringUtils.isBlank(menu.name)) {
            throw RRException("菜单名称不能为空")
        }

        if (menu.parentId == null) {
            throw RRException("上级菜单不能为空")
        }

        //菜单
        if (menu.type == MenuType.MENU.value) {
            if (StringUtils.isBlank(menu.url)) {
                throw RRException("菜单URL不能为空")
            }
        }

        //上级菜单类型
        var parentType = MenuType.CATALOG.value
        if (menu.parentId!!.toInt() != 0) {
            val parentMenu = sysMenuService.queryObject(menu.parentId)
            parentType = parentMenu.type!!
        }

        //目录、菜单
        if (menu.type == MenuType.CATALOG.value || menu.type == MenuType.MENU.value) {
            if (parentType != MenuType.CATALOG.value) {
                throw RRException("上级菜单只能为目录类型")
            }
            return
        }

        //按钮
        if (menu.type == MenuType.BUTTON.value) {
            if (parentType != MenuType.MENU.value) {
                throw RRException("上级菜单只能为菜单类型")
            }
        }
    }
}
