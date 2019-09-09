package com.distribution.weixin.mp.controller

import com.alibaba.fastjson.JSON
import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.api.service.UserService
import com.distribution.modules.dis.entity.DisFans
import com.distribution.modules.dis.service.DisFansService
import com.distribution.modules.dis.service.DisMemberInfoService
import com.distribution.queue.LevelUpSender
import com.distribution.weixin.service.WeiXinService
import com.distribution.weixin.utils.WxUtils
import com.google.common.collect.Lists
import com.vdurmont.emoji.EmojiParser
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.chanjar.weixin.common.bean.menu.WxMenu
import me.chanjar.weixin.common.bean.menu.WxMenuButton
import me.chanjar.weixin.common.exception.WxErrorException
import me.chanjar.weixin.mp.api.WxMpService
import me.chanjar.weixin.mp.api.impl.WxMpMenuServiceImpl
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken
import me.chanjar.weixin.mp.bean.result.WxMpUser
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData
import org.apache.commons.lang3.StringUtils
import org.bouncycastle.asn1.x500.style.RFC4519Style.name
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.MessageFormat
import java.time.LocalDateTime
import java.util.*

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.weixin.mp.controller
 
 * @create 2018/5/27-15:54
 */
@Api("微信接口")
@Controller
@RequestMapping("/api")
class WeiXinMpController {
    @Autowired
    private lateinit var wxMpService: WxMpService
    @Autowired
    private lateinit var weiXinService: WeiXinService
    @Autowired
    private lateinit var disFansService: DisFansService
    @Autowired
    private lateinit var disMemberInfoService: DisMemberInfoService
    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var levelUpSender: LevelUpSender
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>
    @Value("\${risk.url}")
    private lateinit var url: String
    @Value("\${wechat.url}")
    private lateinit var weixinUrl: String
    @Value("\${risk.return-url}")
    private lateinit var returnUrl: String

    private val log: Logger = LoggerFactory.getLogger(WeiXinMpController::class.java)

    @AuthIgnore
    @GetMapping("/weixin")
    @ResponseBody
    fun check(signature: String, timestamp: String, nonce: String, echostr: String): String {
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr)
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw IllegalArgumentException("请求参数非法，请核实!")
        }
        return if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            "非法请求"
        } else echostr

    }

    /**
     * 生成用户授权链接,用于获取授权code
     *
     * @param mobile 推荐人ID
     * @return
     */
    @ApiOperation(value = "生成用户授权链接,用于获取授权code")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "会员手机号")
    @AuthIgnore
    @GetMapping("/weixin/oauthUrl")
    @ResponseBody
    @Throws(UnsupportedEncodingException::class)
    fun buildOauthUrl(mobile: String): String {
        var localMobile = mobile
        if (StringUtils.isBlank(localMobile)) {
            localMobile = userService.queryObject("1")!!.mobile!!
        }
        val map = mapOf("mobile" to localMobile, "to" to "/")
        return wxMpService.oauth2buildAuthorizationUrl(returnUrl, "snsapi_userinfo",
                URLEncoder.encode(JSON.toJSONString(map), "UTF-8"))
    }


    @ApiOperation(value = "生成用户分享链接")
    @ApiImplicitParams(ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "会员手机号"), ApiImplicitParam(paramType = "query", dataType = "string", name = "to", value = "跳转链接"))
    @AuthIgnore
    @GetMapping("/weixin/shareUrl")
    fun buildShareUrl(mobile: String, to: String): String {
        var localMobile = mobile
        if (StringUtils.isBlank(localMobile)) {
            localMobile = userService.queryObject("1")!!.mobile!!
        }
        val map = HashMap<String, Any>()
        map["mobile"] = localMobile
        try {
            map["to"] = URLEncoder.encode(to, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            log.error("", e)
        }

        var url: String? = null
        try {
            url = wxMpService.oauth2buildAuthorizationUrl(returnUrl, "snsapi_userinfo",
                    URLEncoder.encode(JSON.toJSONString(map), "UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            log.error("", e)
        }

        log.info("URL={}", url)
        return "redirect:$url"
    }


    @AuthIgnore
    @GetMapping("/createMenu")
    @ResponseBody
    fun menu(): String {
        val menuService = WxMpMenuServiceImpl(wxMpService)
        val button = WxMenuButton()
        button.name = "首页"
        button.type = "view"
        button.url = weixinUrl
        val menu = WxMenu()
        menu.buttons = listOf(button)
        val create: String
        try {
            create = menuService.menuCreate(menu)
            log.info(create)
        } catch (e: WxErrorException) {
            log.error("创建菜单异常", e)
            return ""
        }

        return create
    }

    /**
     * 获取用户信息(scope为 snsapi_userinfo)
     * 锁粉
     *
     * @param code
     * @param state 推荐人ID
     * @return
     */
    @AuthIgnore
    @ApiOperation(value = "微信授权回调地址", notes = "接收微信回调获取用户信息+锁粉")
    @ApiImplicitParams(ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "微信code"), ApiImplicitParam(paramType = "query", dataType = "string", name = "state", value = "会员手机号"))
    @GetMapping("/getUserInfo")
    fun getUserInfo(code: String, state: String): String {
        var weixinState = state
        weixinState = StringUtils.substringBetween(weixinState, "{", "}")
        var mobile = StringUtils.substringBetween(weixinState.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1], "\"", "\"")
        val to = StringUtils.substringBetween(weixinState.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1], "\"", "\"")
        log.info("mobile={},to={}", mobile, to)
        val returnUrl: String
        if (StringUtils.isBlank(mobile)) {
            mobile = userService.queryObject("1")!!.mobile
        }
        val accessToken: WxMpOAuth2AccessToken
        val user: WxMpUser
        try {
            //通过code换取网页授权access_token
            accessToken = wxMpService.oauth2getAccessToken(code)
            //拉取用户信息
            user = wxMpService.oauth2getUserInfo(accessToken, null)
            log.info("用户信息为{}", user)
        } catch (e: WxErrorException) {
            log.error("拉取用户信息异常", e)
            return "redirect:$url"

        }

        returnUrl = if (StringUtils.isNotBlank(to)) {
            """$url?openId=${user.openId}&to=$to"""
        } else {
            """$url?openId=${user.openId}"""
        }
        //判断该用户是否已关注或已注册
        if (disFansService.queryByOpenId(user.openId) != null || disMemberInfoService.queryByOpenId(user.openId) != null) {
            return "redirect:$returnUrl"
        }

        val disFans = DisFans()
        disFans.id = CommonUtils.uuid
        disFans.wechatId = user.openId
        disFans.wechatImg = user.headImgUrl
        disFans.wechatNickname = EmojiParser.removeAllEmojis(user.nickname)
        val workerId = redisTemplate.opsForValue().increment("worker_id", 1)!!
        disFans.workerId = workerId
        //查询推荐人是否存在
        val disMemberInfo = disMemberInfoService.queryByMobile(mobile)
        if (disMemberInfo != null) {
            //如果是自己,直接返回
            if (user.openId == disMemberInfo.openId) {
                return "redirect:$returnUrl"
            }
            //关联推荐人
            disFans.disMemberInfo = disMemberInfo
            try {
                disFansService.save(disFans)
                //异步执行会员升级逻辑
                levelUpSender.send(JSON.toJSONString(disMemberInfo))
            } catch (e: Exception) {
                log.error("保存锁粉信息异常", e)
            }

            //发送新会员加入模板信息
            GlobalScope.launch {
                try {
                    //构造新会员加入模板信息
                    val templateDataList = Lists.newArrayList(
                            WxMpTemplateData("first", MessageFormat.format("尊敬的会员：{0}，恭喜您，有新会员加入！", name)),
                            WxMpTemplateData("keyword1", workerId.toString()),
                            WxMpTemplateData("keyword2", DateUtils.formatDateTime(LocalDateTime.now())),
                            WxMpTemplateData("remark", "感谢您的努力")
                    )
                    WxUtils.buildAndSendTemplateMsg(disMemberInfo.openId!!, "-IxOfOp7QmIBKcS2MZ8c9WT35bBT6HO4yKfcj3C3u0E",
                            templateDataList, weiXinService)
                } catch (e: WxErrorException) {
                    log.error("发送消息异常", e)
                }
            }
        }

        return "redirect:$returnUrl"
    }

    /**
     * 生成jssdk对应配置
     *
     * @return
     */
    @ApiOperation(value = "生成jssdk对应配置")
    @AuthIgnore
    @GetMapping("/weixin/jsConfig")
    @ResponseBody
    fun jsConfig(url: String): Result {
        return try {
            Result().ok().put("config", wxMpService.createJsapiSignature(url))
        } catch (e: WxErrorException) {
            log.error("生成jssdk对应配置异常", e)
            Result().error(msg = "生成jssdk对应配置异常")
        }

    }
}
