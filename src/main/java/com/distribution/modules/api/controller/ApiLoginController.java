package com.distribution.modules.api.controller;


import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.config.JWTConfig;
import com.distribution.modules.api.entity.UserEntity;
import com.distribution.modules.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * API登录授权
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:31
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api("登录接口")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTConfig jwtConfig;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 登录或注册,注册过校验验证码,成功登陆,没有注册过执行注册逻辑
     */
    @AuthIgnore
    @PostMapping("loginOrRegister")
    @ApiOperation(value = "登录", notes = "登录说明")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "captcha", value = "验证码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "openId", value = "微信open_id")
    })
    public Result loginOrRegister(String mobile, String captcha, String openId) {
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(captcha)) {
            return Result.error("手机号或验证码不能为空");
        }
        //校验验证码
        //根据手机号获取验证码
        String code = redisTemplate.opsForValue().get(mobile);
        if (!captcha.equals(code)) {
            return Result.error("验证码不正确");
        }
        //用户登录
        UserEntity userEntity = userService.queryByMobile(mobile);
        //如果已注册,登陆
        if (userEntity != null) {
            //生成token
            String token = jwtConfig.generateToken(userEntity.getUserId());
            Map<String, Object> map = new HashMap<>(16);
            map.put("token", token);
            map.put("expire", jwtConfig.getExpire());
            return Result.ok(map);
        } else { //没有就注册
            try {
                //默认密码就是手机号
                userService.save(mobile, mobile, openId);
            } catch (Exception e) {
                log.error("注册用户异常", e);
                return Result.error("注册用户异常");
            }
        }
        return Result.ok();
    }

}
