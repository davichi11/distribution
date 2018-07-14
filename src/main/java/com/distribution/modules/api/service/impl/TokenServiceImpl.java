package com.distribution.modules.api.service.impl;

import com.distribution.modules.api.dao.TokenDao;
import com.distribution.modules.api.entity.TokenEntity;
import com.distribution.modules.api.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service("tokenService")
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 12小时后过期
     */
    private final static int EXPIRE = 3600 * 12;

    @Override
    public TokenEntity queryByUserId(Long userId) {
        return tokenDao.queryByUserId(userId);
    }

    @Override
    public TokenEntity queryByToken(String token) {
        return tokenDao.queryByToken(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(TokenEntity token) throws Exception {
        tokenDao.save(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TokenEntity token) throws Exception {
        tokenDao.update(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createToken(long userId) throws Exception {
        //生成一个token
        String token = UUID.randomUUID().toString();
        //当前时间
        LocalDate now = LocalDate.now();

        //过期时间
        LocalDateTime expireTime = LocalDateTime.now().plusHours(12);

        //判断是否生成过token
        //从Redis中根据token获取用户ID
        String user = (String) redisTemplate.opsForValue().get(token);
//        TokenEntity tokenEntity = queryByUserId(userId);
        if (StringUtils.isBlank(user)) {
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            //向Redis中存入token,12小时后过期
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(token, String.valueOf(userId), EXPIRE, TimeUnit.SECONDS);
            save(tokenEntity);
//        } else {
//            tokenEntity.setToken(token);
//            tokenEntity.setUpdateTime(now);
//            tokenEntity.setExpireTime(expireTime);
//
//            //更新token
//            update(tokenEntity);
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("token", token);
        map.put("expire", EXPIRE);

        return map;
    }
}
