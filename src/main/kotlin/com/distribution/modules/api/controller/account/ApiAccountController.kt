package com.distribution.modules.api.controller.account

import com.alipay.api.AlipayApiException
import com.distribution.ali.pay.AliPayParams
import com.distribution.ali.pay.AliPayUtils
import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.Constant
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.account.entity.MemberAccount
import com.distribution.modules.account.entity.MemberAccountHistory
import com.distribution.modules.account.service.MemberAccountHistoryService
import com.distribution.modules.account.service.MemberAccountService
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.api.pojo.vo.WithdrawalVo
import com.distribution.modules.dis.service.DisMemberInfoService
import com.distribution.modules.dis.service.DisProfiParamService
import com.distribution.modules.dis.service.OrderHistoryService
import com.distribution.modules.memeber.entity.WithdrawalInfo
import com.distribution.modules.memeber.service.WithdrawalInfoService
import com.distribution.weixin.service.WeiXinService
import com.distribution.weixin.utils.WxUtils
import com.github.pagehelper.PageHelper
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData
import org.apache.commons.collections.MapUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.text.MessageFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @create 2018/6/8-17:28
 */
@Api("用户账户接口")
@RestController
@RequestMapping("/api")
@CrossOrigin
class ApiAccountController {
    @Autowired
    private lateinit var disMemberInfoService: DisMemberInfoService
    @Autowired
    private lateinit var withdrawalInfoService: WithdrawalInfoService
    @Autowired
    private lateinit var memberAccountService: MemberAccountService
    @Autowired
    private lateinit var memberAccountHistoryService: MemberAccountHistoryService
    @Autowired
    private lateinit var weiXinService: WeiXinService
    @Autowired
    private lateinit var profiParamService: DisProfiParamService
    @Autowired
    private lateinit var orderHistoryService: OrderHistoryService
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>
    @Autowired
    private lateinit var modelMapper: ModelMapper

    private val log = LoggerFactory.getLogger(ApiAccountController::class.java)

    /**
     * 创建用户账户信息
     *
     * @param mobile
     * @param alipayAccount
     * @return
     */
    @ApiOperation(value = "创建或更新用户账户信息")
    @ApiImplicitParams(ApiImplicitParam(paramType = "path", dataType = "string", name = "mobile", value = "手机号", required = true), ApiImplicitParam(paramType = "query", dataType = "string", name = "alipay_account", value = "支付宝账户", required = true))
    @PostMapping("/memberAccount/{mobile}")
    fun memberAccount(@PathVariable("mobile") mobile: String, @RequestParam("alipay_account") alipayAccount: String): Result {
        if (StringUtils.isBlank(alipayAccount)) {
            return Result().error(msg = "支付宝账户不能为空")
        }

        val member = disMemberInfoService.queryByMobile(mobile) ?: return Result().error(msg = "没有此用户")

        var memberAccount = memberAccountService.selectMemberAccountByUserId(mobile)
        if (memberAccount == null) {
            memberAccount = MemberAccount()
            memberAccount.member = member
            memberAccount.memberType = "1"
            memberAccount.aliPayAccount = alipayAccount
            memberAccount.isDelete = "1"
            memberAccount.addTime = DateUtils.formatDateTime(LocalDateTime.now())
            memberAccount.accountId = CommonUtils.uuid
            memberAccount.memberAmount = BigDecimal(0)
            return try {
                memberAccountService.save(memberAccount)
                Result().ok("用户账户信息保存成功")
            } catch (e: Exception) {
                log.error("保存用户账户信息异常", e)
                Result().error(msg = "保存用户账户信息异常")
            }

        } else {
            memberAccount.aliPayAccount = alipayAccount
            return try {
                memberAccountService.update(memberAccount)
                Result().ok("用户账户信息更新成功")
            } catch (e: Exception) {
                log.error("保存用户账户信息异常", e)
                Result().error(msg = "更新用户账户信息异常")
            }

        }
    }

    @ApiOperation(value = "用户支付宝账户更新")
    @ApiImplicitParams(ApiImplicitParam(paramType = "path", dataType = "string", name = "mobile", value = "手机号", required = true), ApiImplicitParam(paramType = "query", dataType = "string", name = "alipay_account", value = "支付宝账户", required = true))
    @PatchMapping("/memberAccount/{mobile}")
    fun updateAliPayAccount(@PathVariable("mobile") mobile: String, @RequestParam("alipay_account") alipayAccount: String): Result {
        if (StringUtils.isBlank(alipayAccount)) {
            return Result().error(msg = "支付宝账户不能为空")
        }
        val memberAccount = memberAccountService.selectMemberAccountByUserId(mobile)
                ?: return Result().error(msg = "请先在账户管理--绑定支付宝账户")
        memberAccount.aliPayAccount = alipayAccount
        return try {
            memberAccountService.update(memberAccount)
            Result().ok("用户支付宝账户更新成功")
        } catch (e: Exception) {
            log.error("用户支付宝账户更新异常", e)
            Result().error(msg = "用户支付宝账户更新异常")
        }

    }

    /**
     * 用户总金额，提现，总收入
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 19:54
     * @Description:
     */
    @GetMapping("/memberAmount")
    @ApiOperation(value = "用户总金额，提现，总收入")
    fun memberAmount(@RequestParam mobile: String): Result {
        val resultMap = mutableMapOf<String, Any>()
        //        Map<String, Object> param = new HashMap<>(2);
        //        param.put("userId",userId);
        //账户信息
        val memberAccount = memberAccountService.selectMemberAccountByUserId(mobile)
                ?: return Result().error(msg = "请先在账户管理--绑定支付宝账户")

        //提现金额
        val withdrawalAmount = withdrawalInfoService.withdrawalAmounts(mobile)
        resultMap["withdrawalAmount"] = withdrawalAmount
        //账户余额
        resultMap["memberAccount"] = memberAccount.memberAmount
        val map = HashMap<String, Any>(6)
        map["accountId"] = memberAccount.accountId
        map["hisType"] = MemberAccountHistory.HisType.INCOME
        val list = memberAccountHistoryService.findList(map)
        var generalIncome = 0.00
        if (list.isNotEmpty()) {
            generalIncome = list.map { value -> value.hisAmount.toDouble() }.sum()
        }
        //收入总金额
        resultMap["generalIncome"] = generalIncome

        return Result().ok(resultMap)
    }


    /**
     * 提现记录
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 20:05
     * @Description:
     */
    @GetMapping("/withdrawals/{mobile}")
    @ApiOperation(value = "提现记录")
    fun memberWithdrawalList(@PathVariable mobile: String, @RequestParam params: Map<String, Any>): Result {
        val param = HashMap<String, Any>(2)
        param["withdrawMobile"] = mobile
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<WithdrawalInfo> { withdrawalInfoService.queryList(param) }
        return Result().ok().put("memberWithdrawalList", pageInfo)
    }


    /**
     * 收益明细
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 20:06
     * @Description:
     */
    @GetMapping("/memberAccountHistory/{mobile}")
    @ApiOperation(value = "收益明细")
    fun memberReturns(@PathVariable mobile: String, @RequestParam params: Map<String, Any>): Result {
        val map = HashMap<String, Any>(2)
        map["mobile"] = mobile
        val member = disMemberInfoService.queryByMobile(mobile)
        map["userId"] = member!!.id!!
        map["hisType"] = MemberAccountHistory.HisType.INCOME
        map.putAll(params)
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(map, "page", 0),
                MapUtils.getInteger(map, "limit", 0)).doSelectPageInfo<MemberAccountHistory> {
            memberAccountHistoryService.findList(map)
        }
        return Result().ok().put("memberWithdrawalList", pageInfo)
    }

    /**
     * 用户提现
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 10:45
     */
    @PostMapping("/withdrawal")
    @ApiOperation(value = "用户提现")
    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    fun saveWithdrawalInfo(@RequestBody withdrawalVo: WithdrawalVo): Result {
        //提现校验
        val account = memberAccountService.selectMemberAccountByUserId(withdrawalVo.withdrawMobile)!!
        if (account.aliPayAccount.isBlank()) {
            return Result().error(msg = "请先在账户管理--绑定支付宝账户")
        }
        if (account.memberAmount < withdrawalVo.withdrawAmount) {
            return Result().error(msg = "提现金额超出可用余额")
        }
        val limitKey = withdrawalVo.withdrawMobile + "_withdrawal"

        val limit = NumberUtils.toDouble(redisTemplate.opsForValue().get(limitKey))
        if (limit >= 500 || withdrawalVo.withdrawAmount.toDouble() >= 500) {
            return Result().error(msg = "已超出每日提现限额")
        }

        val countKey = withdrawalVo.withdrawMobile + "_count"
        var count = NumberUtils.toInt(redisTemplate.opsForValue().get(countKey) ?: "0")
        if (count >= 2) {
            return Result().error(msg = "已超出每日提现次数")
        }

        val orderId = AliPayUtils.generateOrderId(withdrawalVo.withdrawMobile,
                Constant.PayType.WITHDRAWAL.value)
        val payParams = AliPayParams()
        payParams.amount = withdrawalVo.withdrawAmount.toDouble()
        payParams.outBizNo = orderId
        payParams.payeeAccount = account.aliPayAccount
        payParams.payeeRealName = account.member.disUserName
        //如果提现金额达到50000,remark必填
        if (withdrawalVo.withdrawAmount > BigDecimal(50000)) {
            payParams.remark = String.format("会员%s提现,金额:%s", account.member.disUserName,
                    withdrawalVo.withdrawAmount)
        }

        //调用支付宝转账接口
        val response = AliPayUtils.transferResponse(payParams)
        if (!response.isSuccess) {
            log.error("会员{}提现失败,case:{},订单号为{}", account.member.disUserName,
                    response.msg, orderId)
            return Result().error(msg = "提现失败")
        }
        //提现成功更新账户余额
        account.memberAmount = account.memberAmount.subtract(withdrawalVo.withdrawAmount)
        memberAccountService.update(account)
        //保存提现记录
        val withdrawalInfo = modelMapper.map(withdrawalVo, WithdrawalInfo::class.java)
        withdrawalInfo.withdrawType = if (response.isSuccess) "1" else "0"
        withdrawalInfo.withdrawNum = orderId
        withdrawalInfo.addTime = DateUtils.formatDateTime(LocalDateTime.now())
        withdrawalInfo.id = CommonUtils.uuid
        withdrawalInfoService.save(withdrawalInfo)
        //当前时间
        val start = LocalDateTime.now()
        //第二天开始时间
        val end = LocalDateTime.now().plusDays(1).with(LocalTime.MIN)
        //计算时间差 获取相差的秒
        val millis = Duration.between(start, end).toMillis()

        //更新每日提现限额
        redisTemplate.opsForValue().set(limitKey, withdrawalVo.withdrawAmount.add(BigDecimal(limit)).toString(),
                millis, TimeUnit.MILLISECONDS)
        //更新每日提现次数
        count += 1
        redisTemplate.opsForValue().set(countKey, (count).toString(), millis, TimeUnit.MILLISECONDS)

        //发送提现成功提醒
        GlobalScope.launch {
            //            wxMpService.templateMsgService.sendTemplateMsg(buildTemplateMsg(account.member.openId,
            //   withdrawalVo.withdrawAmount.toString(), withdrawalInfo.withdrawName))
            val templateDataList = listOf(
                    WxMpTemplateData("first", MessageFormat.format("提现成功！", withdrawalInfo.withdrawName)),
                    WxMpTemplateData("keyword1", withdrawalVo.withdrawAmount.toString()),
                    WxMpTemplateData("keyword2", DateUtils.formatDateTime(LocalDateTime.now())),
                    WxMpTemplateData("remark", "感谢您的使用")
            )
            WxUtils.buildAndSendTemplateMsg(account.member.openId!!, "g-fARpDMBjuMPPnAQnUgUN1YLivLZQvhnAFaELvV_bU",
                    templateDataList, weiXinService)
        }
        return Result().ok()
    }

    /**
     * 购买会员 支付回调
     *
     * @param request
     * @return
     */
    @AuthIgnore
    @RequestMapping("/alipayCallback")
    fun alipayCallback(request: HttpServletRequest, response: HttpServletResponse): String {
        val parameterMap = request.parameterMap
        val params = mutableMapOf<String, String>()
        parameterMap.forEach { k, v -> params[k] = v.joinToString(",") }
        log.info("接收支付宝支付回调,参数为{}", params)
        //判断支付结果
        if ("TRADE_SUCCESS" != params["trade_status"]) {
            log.info("支付失败,参数为{}", params)
            return "failure"
        }
        try {
            if (!AliPayUtils.signVerified(params)) {
                log.error("验签失败")
                return "failure"
            }
            //从回传参数中取出购买的会员等级
            val level = params["passback_params"]
            if (StringUtils.isBlank(level)) {
                log.info("回传参数中没有会员等级")
                return "failure"
            }
            val orderNo = params["out_trade_no"]!!

            //更新订单状态
            val orderHistory = orderHistoryService.selectByOrderId(orderNo)
            if (orderHistory == null) {
                log.error("没有{}该订单信息", orderNo)
                return "failure"
            }
            orderHistoryService.updateOrderStatus(1, orderNo)
            //根据支付宝账户查询对应的会员信息
            val member = disMemberInfoService.queryByMobile(orderHistory.mobile) ?: return "failure"

            //升级会员
            member.disLevel = NumberUtils.toInt(level)
            //用户类型升级为会员
            if ("0" == member.disUserType) {
                member.disUserType = "1"
            }
            disMemberInfoService.update(member)
            //调用分润
            var money = 0.00
            when (level) {
                "1" -> money = 300.00
                "2" -> money = 200.00
                "3" -> money = 100.00
            }

            GlobalScope.launch {
                profiParamService.doFeeSplitting(member, money, true)
                //构造模板消息
                val templateDataList = listOf(
                        WxMpTemplateData("first", MessageFormat.format("尊敬的会员：{0}，您的会员已经成功开通！", member.disUserName)),
                        WxMpTemplateData("keyword1", member.disLevel.toString()),
                        WxMpTemplateData("keyword2", level),
                        WxMpTemplateData("keyword3", " "),
                        WxMpTemplateData("remark", "感谢您的使用")
                )
                //生级成功发送消息
                WxUtils.buildAndSendTemplateMsg(member.openId!!, "kfKckhHo6GnTqO35SPHgQYKkomi1HV9kXsGbm9AO2Y8",
                        templateDataList, weiXinService)
            }
            return "success"

        } catch (e: AlipayApiException) {
            log.error("回调参数验签异常", e)
            return "failure"
        } catch (e: Exception) {
            log.error("会员升级异常", e)
            return "failure"
        }

    }

}
