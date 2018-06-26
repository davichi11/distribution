package com.distribution.modules.api.controller;


import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.service.UserService;
import com.distribution.queue.NotifySender;
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

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * 注册
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-26 17:27
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api("注册接口")
public class ApiRegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private NotifySender sender;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 手机号正则
     */
    private final Pattern p = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$");

    /**
     * 注册
     */
    @AuthIgnore
    @PostMapping("register")
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "password", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "captcha", value = "验证码", required = true)
    })
    public Result register(String mobile, String password,String captcha) {
        if (StringUtils.isBlank(mobile)||StringUtils.isBlank(captcha) || StringUtils.isBlank(password)) {
            return Result.error("手机号,密码或验证码不能为空");
        }
        //根据手机号获取验证码
        String code = redisTemplate.opsForValue().get(mobile);
        if (!captcha.equals(code)) {
            return Result.error("验证码不正确");
        }
        try {
            userService.save(mobile, password);
        } catch (Exception e) {
            log.error("注册异常", e);
            return Result.error("注册异常");
        }

        return Result.ok("注册成功");
    }

    /**
     * 发送短信验证码
     * @param mobile
     * @return
     */
    @AuthIgnore
    @PostMapping("/sendCaptcha")
    @ApiOperation(value = "发送验证码")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true)
    public Result sendCaptcha(String mobile) {
        if (StringUtils.isBlank(mobile) || !p.matcher(mobile).matches()) {
            return Result.error("手机号不正确");
        }
        String captcha = CommonUtils.getRandom();
        //放入Redis缓存,60秒过期
        redisTemplate.opsForValue().set(mobile, captcha, 60, TimeUnit.SECONDS);
        String msg = MessageFormat.format("【】您的短信验证码为:{0}",captcha);
        sender.send(msg);
        return Result.ok().put("captcha", captcha);
    }
}
