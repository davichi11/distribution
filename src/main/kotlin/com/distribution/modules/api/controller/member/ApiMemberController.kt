package com.distribution.modules.api.controller.member

import com.alibaba.fastjson.JSON
import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.Result
import com.distribution.modules.account.entity.MemberAccount
import com.distribution.modules.account.service.MemberAccountService
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.api.config.JWTConfig
import com.distribution.modules.api.entity.UserEntity
import com.distribution.modules.api.pojo.vo.DisMemberVO
import com.distribution.modules.api.pojo.vo.POSVO
import com.distribution.modules.api.service.UserService
import com.distribution.modules.dis.entity.DisFans
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.service.DisFansService
import com.distribution.modules.dis.service.DisMemberInfoService
import com.distribution.modules.pos.entity.PosApplyEntity
import com.distribution.modules.pos.service.PosApplyService
import com.distribution.modules.sys.service.SysConfigService
import com.distribution.weixin.service.WeiXinService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.apache.commons.lang3.StringUtils
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @Description TODO(描述)
 * @create 2018/5/11-23:09
 */
@Api("会员信息相关接口")

@RestController
@RequestMapping("/api")
@CrossOrigin
class ApiMemberController {
    @Autowired
    private lateinit var disFansService: DisFansService
    @Autowired
    private lateinit var disMemberInfoService: DisMemberInfoService

    @Autowired
    private lateinit var memberAccountService: MemberAccountService
    @Autowired
    private lateinit var weiXinService: WeiXinService
    @Autowired
    private lateinit var configService: SysConfigService
    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var posApplyService: PosApplyService
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>
    @Autowired
    private lateinit var modelMapper: ModelMapper
    @Autowired
    private lateinit var jwtConfig: JWTConfig

    private val log = LoggerFactory.getLogger(ApiMemberController::class.java)


    val vipList: Result
        @AuthIgnore
        @ApiOperation("获取VIP购买列表")
        @GetMapping("/vip")
        get() {
            val config = configService.getValue("level_price", "")
            val json = JSON.parseObject(config)
            return Result().ok().put("level", json)
        }

    /**
     * 我的团队
     *
     * @param mobile
     * @return
     */
    @GetMapping("team")
    @ApiOperation(value = "我的团队")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "会员手机号", required = true)
    fun myTeam(mobile: String): Result {
        if (StringUtils.isBlank(mobile)) {
            return Result().error(msg = "手机号不能为空")
        }
        val member = disMemberInfoService.queryByMobile(mobile) ?: return Result().error(msg = "没有该用户")
        //查询所有锁粉信息
        val fansParam = HashMap<String, Any>(2)
        fansParam["memberId"] = member.id!!
        val disFansList = disFansService.queryList(fansParam)?.asSequence()?.map { disFans ->
            val memberInfo = disMemberInfoService.queryByOpenId(disFans.wechatId) ?: DisMemberInfoEntity()
            getDisMemberVO(disFans, memberInfo)
        }?.filter { vo -> "0" == vo.disUserType }?.toList() ?: listOf()

        //所有代理信息
        val children = member.disMemberChildren?.asSequence()?.filter { m -> "1" == m.disUserType }?.map { memberInfo ->
            val disFans = disFansService.queryByOpenId(memberInfo.openId!!) ?: DisFans()
            memberInfo.userEntity = userService.queryByMemberId(memberInfo.id!!)!!
            getDisMemberVO(disFans, memberInfo)
        }?.toList() ?: listOf()

        //返回数据
        val map = HashMap<String, Any>()
        map["countFans"] = disFansList.size
        map["fansList"] = disFansList
        map["countChildren"] = children.size
        map["children"] = children
        return Result().ok().put("results", map)
    }

    private fun getDisMemberVO(disFans: DisFans, memberInfo: DisMemberInfoEntity): DisMemberVO {
        val memberVO = DisMemberVO()
        memberVO.disUserName = memberInfo.disUserName ?: disFans.wechatNickname
        memberVO.disUserType = memberInfo.disUserType ?: "0"
        memberVO.disLevel = memberInfo.disLevel ?: 0
        memberVO.mobile = memberInfo.userEntity?.mobile ?: ""
        memberVO.openId = disFans.wechatId
        memberVO.addTime = StringUtils.substring(memberInfo.addTime ?: "", 0, 10)
        memberVO.nickName = disFans.wechatNickname
        memberVO.imgUrl = disFans.wechatImg
        memberVO.workerId = disFans.workerId
        return memberVO
    }


    @GetMapping("/memberAccount/{mobile}")
    @ApiOperation(value = "查询用户账户信息")
    fun memberAccountInfo(@PathVariable("mobile") mobile: String): Result {
        val memberAccount = memberAccountService.selectMemberAccountByUserId(mobile) ?: MemberAccount()

        return Result().ok().put("alipayAccount", memberAccount.aliPayAccount)
    }

    /**
     * 查询用户信息
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 11:40
     */
    @AuthIgnore
    @GetMapping("/disMember/{mobile}")
    @ApiOperation(value = "查询用户信息")
    fun disMember(@PathVariable("mobile") mobile: String): Result {
        val member = disMemberInfoService.queryByMobile(mobile) ?: return Result().error(msg = "没有查询到用户信息")

        return Result().ok().put("disMember", member)
    }

    @AuthIgnore
    @GetMapping("/disMember/workerId/{workerId}")
    @ApiOperation(value = "根据工号查询用户信息")
    fun findByWorkerId(@PathVariable("workerId") workerId: String): Result {
        val disMemberInfoEntity = disMemberInfoService.findByWorkerId(workerId)
                ?: return Result().error(msg = "没有该用户信息")

        return Result().ok().put("disMember", disMemberInfoEntity)

    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("/memberByToken")
    fun getMemberInfoByToken(token: String, request: HttpServletRequest): Result {
        var t = token
        if (StringUtils.isBlank(t)) {
            if (StringUtils.isNotBlank(request.getHeader("token"))) {
                t = request.getHeader("token")
            } else {
                return Result().error(msg = "token不能为空")
            }
        }
        val userId = jwtConfig.getClaimByToken(t)!!.subject
//        val userId = claims!!.subject
        val userEntity = userService.queryObject(userId) ?: return Result().error(msg = "请先注册")
        val memberInfoEntity = disMemberInfoService.queryByMobile(userEntity.mobile!!)
        val memberVO = buildMemberVO(userEntity, memberInfoEntity!!)
        return Result().ok().put("memberInfo", memberVO)
    }

    private fun buildMemberVO(userEntity: UserEntity, memberInfo: DisMemberInfoEntity): DisMemberVO {
        val disMemberVO = DisMemberVO()
        val fans = disFansService.queryByOpenId(memberInfo.openId!!)
        disMemberVO.disUserName = memberInfo.disUserName!!
        disMemberVO.disUserType = memberInfo.disUserType!!
        disMemberVO.disLevel = memberInfo.disLevel!!
        disMemberVO.mobile = userEntity.mobile!!
        disMemberVO.idCode = memberInfo.idCode!!
        disMemberVO.openId = memberInfo.openId!!
        disMemberVO.addTime = memberInfo.addTime!!
        if (fans != null) {
            disMemberVO.nickName = fans.wechatNickname
            disMemberVO.imgUrl = fans.wechatImg
            disMemberVO.workerId = fans.workerId
        }
        return disMemberVO
    }


    @AuthIgnore
    @GetMapping("/upLevel/{openId}")
    @ApiOperation(value = "查询用户上级信息")
    fun getUpLevel(@PathVariable("openId") openId: String): Result {
        val fans = disFansService.queryByOpenId(openId)
        //获取上级会员信息
        val parent = fans!!.disMemberInfo ?: return Result().error(msg = "用户不存在")
        val disFans = disFansService.queryByOpenId(parent.openId!!) ?: return Result().error(msg = "用户不存在")
        val memberVO = DisMemberVO()
        memberVO.disUserName = parent.disUserName!!
        memberVO.disUserType = parent.disLevel.toString()
        memberVO.mobile = userService.queryByMemberId(parent.id!!)!!.mobile!!
        memberVO.openId = parent.openId!!
        memberVO.nickName = disFans.wechatNickname
        memberVO.imgUrl = disFans.wechatImg
        memberVO.disLevel = parent.disLevel!!

        return Result().ok().put("upLevel", memberVO)
    }


    @AuthIgnore
    @ApiOperation(value = "查询微信关注者信息")
    @GetMapping("/disFans")
    fun disFansInfo(@RequestParam openId: String): Result {
        if (StringUtils.isBlank(openId)) {
            return Result().error(msg = "openID不能为空")
        }
        val fans = disFansService.queryByOpenId(openId) ?: return Result().error(msg = "没有该用户")
        val memberVO = DisMemberVO()
        val member = disMemberInfoService.queryByOpenId(openId)
        if (member != null) {
            BeanUtils.copyProperties(member, memberVO)
            memberVO.token = jwtConfig.generateToken(member.userEntity!!.userId!!)
            memberVO.expire = jwtConfig.expire
            memberVO.mobile = member.userEntity!!.mobile!!
        }
        memberVO.imgUrl = fans.wechatImg
        memberVO.nickName = fans.wechatNickname
        memberVO.workerId = fans.workerId
        memberVO.openId = openId
        return Result().ok().put("weixinInfo", memberVO)
    }

    @ApiOperation("更新会员手机号")
    @ApiImplicitParams(
            ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "手机号", required = true),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "oldMobile", value = "旧手机号", required = true),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "captcha", value = "验证码", required = true))
    @PostMapping("/updateMobile")
    fun updateMobile(mobile: String, oldMobile: String, captcha: String): Result {
        if (redisTemplate.opsForValue().get(mobile) != captcha) {
            return Result().error(msg = "验证码不正确")
        }
        val userEntity = userService.queryByMobile(oldMobile) ?: return Result().error(msg = "用户不存在")
        if (userService.queryByMobile(mobile) != null) {
            return Result().error(msg = "该手机号己被注册")
        }
        userEntity.mobile = mobile

        return try {
            userService.update(userEntity)
            Result().ok("更新用户手机号成功")
        } catch (e: Exception) {
            log.error("更新用户手机号异常", e)
            return Result().error(msg = "更新用户手机号异常")
        }
    }

    @AuthIgnore
    @ApiOperation("添加POS机申请")
    @ApiImplicitParam(paramType = "body", dataType = "POSVO", name = "posvo", value = "POS机申请", required = true)
    @PostMapping("/pos")
    fun applyPOS(@RequestBody posvo: POSVO): Result {
        val applyEntity = modelMapper.map(posvo, PosApplyEntity::class.java)
        applyEntity.id = CommonUtils.uuid
        return try {
            posApplyService.save(applyEntity)
            Result().ok("添加POS机申请成功")
        } catch (e: Exception) {
            log.error("添加POS机申请异常", e)
            Result().error(msg = "添加POS机申请异常")
        }

    }


    /**
     * 更新会员信息
     *
     * @param mobile
     * @param memberVO
     * @return
     */
    @ApiOperation("更新会员信息")
    @ApiImplicitParams(
            ApiImplicitParam(paramType = "path", dataType = "string", name = "mobile", value = "手机号", required = true),
            ApiImplicitParam(paramType = "body", dataType = "DisMemberVO", name = "memberVO", value = "手机号", required = true)
    )
    @PatchMapping("/disMember/{mobile}")
    fun updateMemberInfo(@PathVariable("mobile") mobile: String, @RequestBody memberVO: DisMemberVO): Result {
        val member = disMemberInfoService.queryByMobile(mobile) ?: return Result().error(msg = "用户不存在")
        BeanUtils.copyProperties(memberVO, member)
        return try {
            disMemberInfoService.update(member)
            Result().ok("更新成功")
        } catch (e: Exception) {
            log.error("更新会员信息异常", e)
            return Result().error(msg = "更新会员信息异常")
        }

    }


}
