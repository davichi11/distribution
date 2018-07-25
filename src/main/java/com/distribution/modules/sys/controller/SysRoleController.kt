package com.distribution.modules.sys.controller

import com.distribution.common.annotation.SysLog
import com.distribution.common.utils.Constant
import com.distribution.common.utils.Result
import com.distribution.common.validator.ValidatorUtils
import com.distribution.modules.sys.entity.SysRoleEntity
import com.distribution.modules.sys.service.SysRoleMenuService
import com.distribution.modules.sys.service.SysRoleService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * 角色管理
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
class SysRoleController : AbstractController() {
    @Autowired
    private lateinit var sysRoleService: SysRoleService
    @Autowired
    private lateinit var sysRoleMenuService: SysRoleMenuService

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    fun list(@RequestParam params: MutableMap<String, Any>): Result {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (userId!!.toInt() != Constant.SUPER_ADMIN) {
            params["createUserId"] = userId!!
        }
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0)!!,
                MapUtils.getInteger(params, "limit", 0)!!).doSelectPageInfo<SysRoleEntity> { sysRoleService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    fun select(): Result {
        val map = HashMap<String, Any>(16)

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (userId!!.toInt() != Constant.SUPER_ADMIN) {
            map["createUserId"] = userId!!
        }
        val list = sysRoleService.queryList(map)

        return Result().ok().put("list", list)
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    fun info(@PathVariable("roleId") roleId: Long): Result {
        val role = sysRoleService.queryObject(roleId)

        //查询角色对应的菜单
        val menuIdList = sysRoleMenuService.queryMenuIdList(roleId)
        role.menuIdList = menuIdList

        return Result().ok().put("role", role)
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    fun save(@RequestBody role: SysRoleEntity): Result {
        ValidatorUtils.validateEntity(role)

        role.createUserId = userId
        try {
            sysRoleService.save(role)
        } catch (e: Exception) {
            logger.error("保存角色异常", e)
        }

        return Result().ok()
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    fun update(@RequestBody role: SysRoleEntity): Result {
        ValidatorUtils.validateEntity(role)

        role.createUserId = userId
        try {
            sysRoleService.update(role)
        } catch (e: Exception) {
            logger.error("更新角色异常", e)
        }

        return Result().ok()
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    fun delete(@RequestBody roleIds: Array<Long>): Result {
        try {
            sysRoleService.deleteBatch(roleIds)
        } catch (e: Exception) {
            logger.error("删除角色异常", e)
        }

        return Result().ok()
    }
}
