package com.distribution.modules.api.controller


import com.distribution.common.utils.Result
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.api.config.JWTConfig
import com.distribution.modules.api.pojo.vo.DisMemberVO
import com.distribution.modules.api.service.UserService
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.service.DisFansService
import com.distribution.modules.dis.service.DisMemberInfoService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * API登录授权
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:31
 */

@RestController
@RequestMapping("/api")
@Api("登录接口")
@CrossOrigin
class ApiLoginController {
    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var memberInfoService: DisMemberInfoService
    @Autowired
    private lateinit var fansService: DisFansService
    @Autowired
    private lateinit var jwtConfig: JWTConfig
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>

    /**
     * 登录或注册,注册过校验验证码,成功登陆,没有注册过执行注册逻辑
     */
    @AuthIgnore
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录,注册过校验验证码,成功登陆")
    @ApiImplicitParams(ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true), ApiImplicitParam(paramType = "query", dataType = "string", name = "captcha", value = "验证码", required = true), ApiImplicitParam(paramType = "query", dataType = "string", name = "openId", value = "微信open_id"))
    fun login(mobile: String, captcha: String, openId: String): Result {
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(captcha)) {
            return Result().error( msg ="手机号或验证码不能为空")
        }
        //校验验证码
        //根据手机号获取验证码
        val code = redisTemplate.opsForValue().get(mobile)
        if (captcha != code) {
            return Result().error( msg ="验证码不正确")
        }
        val disMemberVO = DisMemberVO()

        //用户登录
        val userEntity = userService.queryByMobile(mobile) ?: return Result().error( msg ="请先注册")
        val memberInfo = memberInfoService.queryByMobile(userEntity.mobile)
        buildMemberVO(mobile, disMemberVO, memberInfo!!)
        //生成token
        val token = jwtConfig.generateToken(userEntity.userId)
        val map = HashMap<String, Any>(16)
        map["token"] = token
        map["expire"] = jwtConfig.expire
        map["user"] = disMemberVO

        return Result().ok(map)
    }

    private fun buildMemberVO(mobile: String, disMemberVO: DisMemberVO, memberInfo: DisMemberInfoEntity) {
        val fans = fansService.queryByOpenId(memberInfo.openId)
        disMemberVO.disUserName = memberInfo.disUserName
        disMemberVO.disUserType = memberInfo.disUserType
        disMemberVO.disLevel = memberInfo.disLevel
        disMemberVO.mobile = mobile
        disMemberVO.idCode = memberInfo.idCode
        disMemberVO.openId = memberInfo.openId
        disMemberVO.addTime = memberInfo.addTime
        if (fans != null) {
            disMemberVO.workerId = fans.workerId
            disMemberVO.nickName = fans.wechatNickname
            disMemberVO.imgUrl = fans.wechatImg
        }
    }

}
