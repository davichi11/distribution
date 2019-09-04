package com.distribution.modules.api.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package com.distribution.modules.api.config
 
 * @create 2017/10/21-12:08
 */

@ConfigurationProperties(prefix = "risk.jwt")
@Component
class JWTConfig {
    var secret: String? = null
    var expire: Long = 0
    var header: String? = null

    /**
     * 生成jwt token
     */
    fun generateToken(userId: String): String {
        //过期时间
        val expireDate = LocalDateTime.now().plusSeconds(expire)

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(Date())
                .setExpiration(Date.from(expireDate.toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
    }

    fun getClaimByToken(token: String): Claims? {
        return try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .body
        } catch (e: Exception) {
//            log.debug("validate is token error ", e)
            null
        }

    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    fun isTokenExpired(expiration: LocalDateTime): Boolean {
        return expiration.isBefore(LocalDateTime.now())
    }
}
