package com.distribution.modules.sys.controller

import com.distribution.common.annotation.SysLog
import com.distribution.common.exception.RRException
import com.distribution.common.utils.Constant
import com.distribution.common.utils.Result
import com.distribution.common.validator.ValidatorUtils
import com.distribution.common.validator.group.AddGroup
import com.distribution.common.validator.group.UpdateGroup
import com.distribution.modules.sys.entity.SysUserEntity
import com.distribution.modules.sys.service.SysUserRoleService
import com.distribution.modules.sys.service.SysUserService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.commons.lang.ArrayUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * 系统用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
class SysUserController : AbstractController() {
    @Autowired
    private lateinit var sysUserService: SysUserService
    @Autowired
    private lateinit var sysUserRoleService: SysUserRoleService

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    fun list(@RequestParam params: MutableMap<String, Any>): Result {
        //只有超级管理员，才能查看所有管理员列表
        if (userId!!.toInt() != Constant.SUPER_ADMIN) {
            params["createUserId"] = userId!!
        }

        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<SysUserEntity> { sysUserService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    fun info(): Result {
        return Result().ok().put("user", user)
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    fun password(password: String, newPassword: String): Result {
        var password = password
        var newPassword = newPassword

        //sha256加密
        password = Sha256Hash(password, user.salt).toHex()
        //sha256加密
        newPassword = Optional.ofNullable(newPassword).orElseThrow { RRException("新密码不为能空") }

        //更新密码
        var count = 0
        try {
            count = sysUserService.updatePassword(userId!!, password, newPassword)
        } catch (e: Exception) {
            logger.error("修改密码异常", e)
        }

        return if (count == 0) {
            Result().error(msg = "原密码不正确")
        } else Result().ok()

    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    fun info(@PathVariable("userId") userId: Long): Result {
        val user = sysUserService.queryObject(userId)

        //获取用户所属的角色列表
        val roleIdList = sysUserRoleService.queryRoleIdList(userId)
        user.roleIdList = roleIdList

        return Result().ok().put("user", user)
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    fun save(@RequestBody user: SysUserEntity): Result {
        ValidatorUtils.validateEntity(user, AddGroup::class.java)

        user.createUserId = userId
        try {
            sysUserService.save(user)
        } catch (e: Exception) {
            logger.error("保存用户异常", e)
            return Result().error(msg = "保存用户异常")
        }

        return Result().ok()
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    fun update(@RequestBody user: SysUserEntity): Result {
        ValidatorUtils.validateEntity(user, UpdateGroup::class.java)

        user.createUserId = userId
        try {
            sysUserService.update(user)
        } catch (e: Exception) {
            logger.error("修改用户异常", e)
            return Result().error(msg = "修改用户异常")
        }

        return Result().ok()
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    fun delete(@RequestBody userIds: Array<Long>): Result {
        if (ArrayUtils.contains(userIds, 1L)) {
            return Result().error(msg = "系统管理员不能删除")
        }

        if (ArrayUtils.contains(userIds, userId)) {
            return Result().error(msg = "当前用户不能删除")
        }

        try {
            sysUserService.deleteBatch(userIds)
        } catch (e: Exception) {
            logger.error("删除用户异常", e)
            return Result().error(msg = "删除用户异常")
        }

        return Result().ok()
    }
}
