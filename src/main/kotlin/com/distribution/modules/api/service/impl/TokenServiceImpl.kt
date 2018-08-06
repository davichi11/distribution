package com.distribution.modules.api.service.impl

import com.distribution.modules.api.dao.TokenDao
import com.distribution.modules.api.entity.TokenEntity
import com.distribution.modules.api.service.TokenService
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit


@Service("tokenService")
class TokenServiceImpl : TokenService {
    @Autowired
    private val tokenDao: TokenDao? = null

    @Autowired
    private val redisTemplate: RedisTemplate<String, Any>? = null

    override fun queryByUserId(userId: Long?): TokenEntity {
        return tokenDao!!.queryByUserId(userId)
    }

    override fun queryByToken(token: String): TokenEntity {
        return tokenDao!!.queryByToken(token)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(token: TokenEntity) {
        tokenDao!!.save(token)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(token: TokenEntity) {
        tokenDao!!.update(token)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun createToken(userId: Long): Map<String, Any> {
        //生成一个token
        val token = UUID.randomUUID().toString()
        //当前时间
        val now = LocalDateTime.now()

        //过期时间
        val expireTime = LocalDateTime.now().plusHours(12)

        //判断是否生成过token
        //从Redis中根据token获取用户ID
        val user = redisTemplate!!.opsForValue().get(token) as String
        //        TokenEntity tokenEntity = queryByUserId(userId);
        if (StringUtils.isBlank(user)) {
            val tokenEntity = TokenEntity()
            tokenEntity.userId = userId
            tokenEntity.token = token
            tokenEntity.updateTime = now
            tokenEntity.expireTime = expireTime

            //保存token
            //向Redis中存入token,12小时后过期
            val operations = redisTemplate.opsForValue()
            operations.set(token, userId.toString(), EXPIRE.toLong(), TimeUnit.SECONDS)
            save(tokenEntity)
            //        } else {
            //            tokenEntity.setToken(token);
            //            tokenEntity.setUpdateTime(now);
            //            tokenEntity.setExpireTime(expireTime);
            //
            //            //更新token
            //            update(tokenEntity);
        }

        val map = HashMap<String, Any>(16)
        map["token"] = token
        map["expire"] = EXPIRE

        return map
    }

    companion object {
        /**
         * 12小时后过期
         */
        private const val EXPIRE = 3600 * 12
    }
}
