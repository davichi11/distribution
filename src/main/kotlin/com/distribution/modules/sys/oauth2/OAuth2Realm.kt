package com.distribution.modules.sys.oauth2

import com.distribution.modules.sys.entity.SysUserEntity
import com.distribution.modules.sys.service.ShiroService
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

/**
 * 认证
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-05-20 14:00
 */
@Component
class OAuth2Realm : AuthorizingRealm() {
    @Autowired
    private val shiroService: ShiroService? = null

    @Autowired
    private val redisTemplate: RedisTemplate<*, *>? = null

    override fun supports(token: AuthenticationToken?): Boolean {
        return token is OAuth2Token
    }

    /**
     * 授权(验证权限时调用)
     */
    override fun doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo {
        val user = principals.primaryPrincipal as SysUserEntity
        val userId = user.userId

        //用户权限列表
        val permsSet = shiroService!!.getUserPermissions(userId!!)

        val info = SimpleAuthorizationInfo()
        info.stringPermissions = permsSet
        return info
    }

    /**
     * 认证(登录时调用)
     */
    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo {
        val accessToken = token.principal as String

        //根据accessToken，查询用户信息
        //token失效
        val userId = redisTemplate!!.opsForValue().get(accessToken) as String?
        if (StringUtils.isBlank(userId)) {
            throw IncorrectCredentialsException("token失效，请重新登录")
        }

        //查询用户信息
        val user = shiroService!!.queryUser(NumberUtils.toLong(userId))
        //账号锁定
        if (user.status == 0) {
            throw Exception("账号已被锁定,请联系管理员")
        }

        return SimpleAuthenticationInfo(user, accessToken, name)
    }
}
