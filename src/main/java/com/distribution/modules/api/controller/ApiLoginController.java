package com.distribution.modules.api.controller;


import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.config.JWTConfig;
import com.distribution.modules.api.entity.UserEntity;
import com.distribution.modules.api.pojo.vo.DisMemberVO;
import com.distribution.modules.api.service.UserService;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.DisFansService;
import com.distribution.modules.dis.service.DisMemberInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private DisMemberInfoService memberInfoService;
    @Autowired
    private DisFansService fansService;
    @Autowired
    private JWTConfig jwtConfig;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 登录或注册,注册过校验验证码,成功登陆,没有注册过执行注册逻辑
     */
    @AuthIgnore
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录,注册过校验验证码,成功登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "captcha", value = "验证码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "openId", value = "微信open_id")
    })
    public Result login(String mobile, String captcha, String openId) {
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(captcha)) {
            return Result.error("手机号或验证码不能为空");
        }
        //校验验证码
        //根据手机号获取验证码
        String code = redisTemplate.opsForValue().get(mobile);
        if (!captcha.equals(code)) {
            return Result.error("验证码不正确");
        }
        DisMemberVO disMemberVO = new DisMemberVO();

        //用户登录
        UserEntity userEntity = userService.queryByMobile(mobile);
        if (userEntity == null) {
            return Result.error("请先注册");
        }
        DisMemberInfoEntity memberInfo = memberInfoService.queryByMobile(userEntity.getMobile());
        buildMemberVO(mobile, disMemberVO, memberInfo);
        //生成token
        String token = jwtConfig.generateToken(userEntity.getUserId());
        Map<String, Object> map = new HashMap<>(16);
        map.put("token", token);
        map.put("expire", jwtConfig.getExpire());
        map.put("user", disMemberVO);

        return Result.ok(map);
    }

    private void buildMemberVO(String mobile, DisMemberVO disMemberVO, DisMemberInfoEntity memberInfo) {
        DisFans fans = fansService.queryByOpenId(memberInfo.getOpenId());
        disMemberVO.setDisUserName(memberInfo.getDisUserName());
        disMemberVO.setDisUserType(memberInfo.getDisUserType());
        disMemberVO.setDisLevel(memberInfo.getDisLevel());
        disMemberVO.setMobile(mobile);
        disMemberVO.setIdCode(memberInfo.getIdCode());
        disMemberVO.setOpenId(memberInfo.getOpenId());
        disMemberVO.setAddTime(memberInfo.getAddTime());
        if (fans != null) {
            disMemberVO.setWorkerId(fans.getWorkerId());
            disMemberVO.setNickName(fans.getWechatNickname());
            disMemberVO.setImgUrl(fans.getWechatImg());
        }
    }

}
