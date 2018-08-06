package com.distribution.modules.sys.service.impl

import com.distribution.common.utils.Constant
import com.distribution.modules.sys.dao.SysMenuDao
import com.distribution.modules.sys.dao.SysUserDao
import com.distribution.modules.sys.entity.SysUserEntity
import com.distribution.modules.sys.entity.SysUserTokenEntity
import com.distribution.modules.sys.service.ShiroService
import com.distribution.modules.sys.service.SysUserTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShiroServiceImpl : ShiroService {
    @Autowired
    private lateinit var sysMenuDao: SysMenuDao
    @Autowired
    private lateinit var sysUserDao: SysUserDao
    @Autowired
    private lateinit var sysUserTokenService: SysUserTokenService

    override fun getUserPermissions(userId: Long): Set<String> {
        var permsList: MutableList<String> = mutableListOf()

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN.toLong()) {
            val menuList = sysMenuDao.queryList(mapOf())
            menuList.map { it.perms }.filter { it != null }.forEach { s: String? -> permsList.add(s!!) }
        } else {
            permsList = sysUserDao.queryAllPerms(userId)
        }
        //用户权限列表
        val permsSet = mutableSetOf<String>()
        permsList.map { perms -> perms.trim().split(",") }.forEach { permsSet.addAll(it) }
        return permsSet
    }

    override fun queryByToken(token: String): SysUserTokenEntity {
        return sysUserTokenService.queryByToken(token)
    }

    override fun queryUser(userId: Long): SysUserEntity {
        return sysUserDao.queryObject(userId)
    }
}
