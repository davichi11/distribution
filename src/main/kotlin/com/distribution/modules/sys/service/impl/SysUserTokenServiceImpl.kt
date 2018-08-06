package com.distribution.modules.sys.service.impl

import com.distribution.common.utils.Result
import com.distribution.modules.sys.dao.SysUserTokenDao
import com.distribution.modules.sys.entity.SysUserTokenEntity
import com.distribution.modules.sys.oauth2.TokenGenerator
import com.distribution.modules.sys.service.SysUserTokenService
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit


/**
 * @author huchunliang
 */
@Service("sysUserTokenService")
class SysUserTokenServiceImpl : SysUserTokenService {
    @Autowired
    private lateinit var sysUserTokenDao: SysUserTokenDao
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>
    private val EXPIRE:Long = 3600 * 12
    override fun queryByUserId(userId: Long?): SysUserTokenEntity {
        return sysUserTokenDao.queryByUserId(userId)
    }

    override fun queryByToken(token: String): SysUserTokenEntity {
        val id = redisTemplate.opsForValue().get(token) as String
        return if (StringUtils.isBlank(id)) {
            SysUserTokenEntity()
        } else queryByUserId(NumberUtils.toLong(id))
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(token: SysUserTokenEntity) {
        sysUserTokenDao.save(token)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(token: SysUserTokenEntity) {
        sysUserTokenDao.update(token)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun createToken(userId: Long): Result {
        //生成一个token
        val token = TokenGenerator.generateValue()

        //当前时间
        val now = LocalDateTime.now()
        //过期时间
        val expireTime = LocalDateTime.now().plusDays(1)

        //判断是否生成过token
        //从Redis中根据token获取用户ID
        val o = redisTemplate.opsForValue().get(token) as String?
        //        SysUserTokenEntity tokenEntity = queryByUserId(userId);
        if (userId != NumberUtils.toLong(o)) {
            val tokenEntity = SysUserTokenEntity()
            tokenEntity.userId = userId
            tokenEntity.token = token
            tokenEntity.updateTime = now
            tokenEntity.expireTime = expireTime
            //保存token
            //向Redis中存入token,12小时后过期
            val operations = redisTemplate.opsForValue()
            operations.set(token, userId.toString(), EXPIRE, TimeUnit.SECONDS)
            //向数据库中存入token
            save(tokenEntity)
            //        } else {
            //            SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
            //            tokenEntity.setToken(token);
            //            tokenEntity.setUpdateTime(now);
            //            tokenEntity.setExpireTime(expireTime);
            //
            //            //更新token
            //            update(tokenEntity);
        }

        return Result().ok().put("token", token).put("expire", EXPIRE)
    }

}
