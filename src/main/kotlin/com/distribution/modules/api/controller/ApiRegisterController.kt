package com.distribution.modules.api.controller


import com.alibaba.fastjson.JSONObject
import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.Result
import com.distribution.modules.account.service.MemberAccountService
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.api.entity.UserEntity
import com.distribution.modules.api.service.IdCardQueryService
import com.distribution.modules.api.service.UserService
import com.distribution.modules.dis.service.DisMemberInfoService
import com.distribution.queue.NotifySender
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 注册
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-26 17:27
 */

@RestController
@RequestMapping("/api")
@Api("注册接口")
@CrossOrigin
class ApiRegisterController {
    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var memberInfoService: DisMemberInfoService
    @Autowired
    private lateinit var idCardQueryService: IdCardQueryService
    @Autowired
    private lateinit var memberAccountService: MemberAccountService
    @Autowired
    private lateinit var sender: NotifySender
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>
    private val log = LoggerFactory.getLogger(ApiRegisterController::class.java)

    /**
     * 注册
     */
    @AuthIgnore
    @PostMapping("register")
    @ApiOperation(value = "注册")
    @ApiImplicitParams(
            ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "password", value = "密码", required = true),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "captcha", value = "验证码", required = true),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "name", value = "真实姓名", required = true),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "idCode", value = "身份证号", required = true),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "openId", value = "微信open_id"),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "fatherWorkerId", value = "上级用户工号")
    )
    fun register(mobile: String, password: String?, name: String?, idCode: String?,
                 captcha: String?, openId: String?, fatherWorkerId: String?): Result {
        var pwd = password
        if (StringUtils.isBlank(mobile)) {
            return Result().error(msg = "手机号不能为空")
        }
        if (StringUtils.isBlank(pwd)) {
            pwd = mobile
        }
        //根据手机号获取验证码
        val code = redisTemplate.opsForValue().get(mobile)
        if (captcha != code) {
            return Result().error(msg = "验证码不正确")
        }

        if (StringUtils.isBlank(openId)) {
            return Result().error(msg = "openid不能为空")
        }
        try {
            //身份证号码实名认证
            if (!idCardQueryService.isMatched(idCode ?: "", name ?: "")) {
                return Result().error(msg = "身份证号不正确,请确认")
            }
            //查询是否有对应的会员
            val param = HashMap<String, Any>(2)
            param["mobile"] = mobile
            val memberList = memberInfoService.queryList(param)
            if (CollectionUtils.isNotEmpty(memberList)) {
                return Result().error(msg = "该手机号已注册")
            }
            userService.save(mobile, pwd, name, idCode, openId, fatherWorkerId)

        } catch (e: Exception) {
            log.error("注册异常", e)
            return Result().error(msg = "注册异常")
        }

        return Result().ok("注册成功")
    }

    @PostMapping("/changePwd")
    fun updatePassword(mobile: String, oldPwd: String, newPwd: String): Result {
        val userId = userService.login(mobile, oldPwd)
        if (StringUtils.isBlank(userId)) {
            return Result().error(msg = "旧密码不正确")
        }
        val userEntity = UserEntity()
        userEntity.userId = userId
        userEntity.mobile = mobile
        userEntity.password = DigestUtils.sha256Hex(newPwd)
        try {
            userService.update(userEntity)
        } catch (e: Exception) {
            log.error("用户修改密码异常", e)
            return Result().error(msg = "用户修改密码异常")
        }

        return Result().ok("修改成功")
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    @AuthIgnore
    @PostMapping("/sendCaptcha")
    @ApiOperation(value = "发送验证码")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true)
    fun sendCaptcha(mobile: String): Result {
        if (StringUtils.isBlank(mobile)) {
            return Result().error(msg = "手机号不正确")
        }
        val captcha = CommonUtils.random
        //放入Redis缓存,60秒过期
        redisTemplate.opsForValue().set(mobile, captcha, 300, TimeUnit.SECONDS)
        val json = JSONObject()
        json["code"] = captcha
        json["cellphone"] = mobile
        sender.send(json.toJSONString())
        return Result().ok().put("captcha", captcha)
    }
}
