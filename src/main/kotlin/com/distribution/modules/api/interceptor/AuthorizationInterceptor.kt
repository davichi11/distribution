package com.distribution.modules.api.interceptor


import com.distribution.common.exception.RRException
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.api.config.JWTConfig
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.time.LocalDateTime
import java.time.ZoneId
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 权限(Token)验证
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:38
 */
@Component
class AuthorizationInterceptor : HandlerInterceptorAdapter() {
    @Autowired
    private lateinit var jwtConfig: JWTConfig

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse?, handler: Any): Boolean {
        val annotation: AuthIgnore?
        if (handler is HandlerMethod) {
            annotation = handler.getMethodAnnotation(AuthIgnore::class.java)
        } else {
            return true
        }

        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true
        }

        //从header中获取token,如果header中不存在token，则从参数中获取token
        val token = if (StringUtils.isBlank(request.getHeader(jwtConfig.header)))
            request.getParameter(jwtConfig.header)
        else
            request.getHeader(jwtConfig.header)


        //token为空
        if (StringUtils.isBlank(token)) {
            throw RRException("token不能为空")
        }

        val claims = jwtConfig.getClaimByToken(token)
                ?: throw RRException(jwtConfig.header!! + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value())
        val localDateTime = LocalDateTime.ofInstant(claims.expiration.toInstant(), ZoneId.systemDefault())
        if (jwtConfig.isTokenExpired(localDateTime)) {
            throw RRException(jwtConfig.header!! + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value())
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(LOGIN_USER_KEY, NumberUtils.toLong(claims.subject))

        return true
    }

    companion object {

        const val LOGIN_USER_KEY = "LOGIN_USER_KEY"
    }
}
