package com.distribution.modules.api.controller.product

import com.distribution.ali.pay.AliPayUtils
import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.Constant
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.api.pojo.vo.LoanOrderVO
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.entity.LoanOrderInfoEntity
import com.distribution.modules.dis.service.DisMemberInfoService
import com.distribution.modules.dis.service.LoanInfoService
import com.distribution.modules.dis.service.LoanOrderInfoService
import com.distribution.weixin.service.WeiXinService
import com.distribution.weixin.utils.WxUtils
import com.github.pagehelper.PageHelper
import com.google.common.collect.Lists
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.chanjar.weixin.common.exception.WxErrorException
import me.chanjar.weixin.mp.api.WxMpService
import me.chanjar.weixin.mp.bean.result.WxMpUser
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.regex.Pattern

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @create 2018/7/6-11:20
 */

@Api("贷款接口")
@RestController
@RequestMapping("/api")
class ApiLoanController {
    @Autowired
    private lateinit var loanInfoService: LoanInfoService
    @Autowired
    private lateinit var loanOrderInfoService: LoanOrderInfoService
    @Autowired
    private lateinit var disMemberInfoService: DisMemberInfoService
    @Autowired
    private lateinit var wxMpService: WxMpService
    @Autowired
    private lateinit var weiXinService: WeiXinService
    /**
     * 身份证正则
     */
    private val idCardNo = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$")

    private val log = LoggerFactory.getLogger(ApiLoanController::class.java)

    @AuthIgnore
    @GetMapping("/loanInfo")
    @ApiOperation("查询所有贷款详情")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "贷款产品ID", value = "id")
    fun getLoanInfo(id: String?): Result {
        return when {
            StringUtils.isBlank(id) -> Result().ok().put("loanInfos",
                    loanInfoService.queryList(mapOf()))
            else -> Result().ok().put("loanInfo", loanInfoService.queryObject(id!!))
        }
    }

    @GetMapping("/loanOrder/{mobile}")
    @ApiOperation("查询用户贷款订单记录")
    @AuthIgnore
    @ApiImplicitParams(
            ApiImplicitParam(paramType = "path", dataType = "string", name = "用户手机号", value = "mobile"),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "页数", value = "page"),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "每页数", value = "limit"),
            ApiImplicitParam(paramType = "query", dataType = "string", name = "状态", value = "status")
    )
    fun getLoanOrderInfo(@PathVariable("mobile") mobile: String,
                         @RequestParam(name = "page", defaultValue = "0") page: Int = 0,
                         @RequestParam(name = "limit", defaultValue = "10") limit: Int = 10,
                         @RequestParam(name = "status", defaultValue = "0") status: Int = 0): Result {
        val member = disMemberInfoService.queryByMobile(mobile)!!
        val memberIds = member.disMemberChildren!!.asSequence().map { it.id }.toMutableList()
        memberIds.add(member.id)
        val param = mapOf("memberIds" to memberIds, "status" to status)
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(page, limit)
                .doSelectPageInfo<LoanOrderInfoEntity> { loanOrderInfoService.queryList(param) }
        return Result().ok().put("loanOrder", pageInfo)
    }

    @PostMapping("/loan")
    @ApiOperation("用户贷款申请")
    @ApiImplicitParam(paramType = "body", dataType = "LoanOrderVO")
    fun applyLoan(@RequestBody loanOrder: LoanOrderVO): Result {
        if (StringUtils.isBlank(loanOrder.orderMobile)) {
            return Result().error(msg = "手机号码不正确")
        }
        if (StringUtils.isBlank(loanOrder.orderIdcardno) || !idCardNo.matcher(loanOrder.orderIdcardno).matches()) {
            return Result().error(msg = "身份证号码不正确")
        }
        if (StringUtils.isBlank(loanOrder.loanId)) {
            return Result().error(msg = "银行代号不能为空")
        }
        val loanInfo = loanInfoService.queryObject(loanOrder.loanId)
        val member = disMemberInfoService.queryByMobile(loanOrder.orderMobile)!!
        //先调用第三方接口保存用户信息并返回url
        val url: String
        try {
            url = loanInfoService.getProductUrl(member, loanOrder.loanId)
        } catch (e: Exception) {
            log.error("申请异常", e)
            return Result().error(msg = "申请异常")
        }

        if (url.isEmpty()) {
            return Result().error(msg = "申请异常")
        }
        //查看是否已办过该贷款产品
        val count = loanOrderInfoService.countLoanOrder(mapOf("mobile" to loanOrder.orderMobile))
        if (count >= 1) {
            return Result().ok().put("url", url)
        }

        try {
            saveOrder(loanOrder, member)
        } catch (e: Exception) {
            log.error("保存订单异常", e)
            return Result().error(msg = "保存订单异常")
        }

        //发送消息前先查询是否已关注
        var wxMpUser = WxMpUser()
        try {
            wxMpUser = wxMpService.userService.userInfo(member.openId, "zh_CN")
        } catch (e: WxErrorException) {
            log.error("查询关注者异常", e)
        }

        if (wxMpUser.openId.isEmpty()) {
            return Result().ok().put("url", url)
        }
        GlobalScope.launch {
            //发送订单信息提醒
            try {
                buildTemplateMsg(member.openId, member.disUserName, loanInfo.loanName, weiXinService)
            } catch (e: WxErrorException) {
                log.error("发送订单信息提醒异常", e)
            }

            //给上级发送消息
            try {
                buildTemplateMsg(member.disMemberParent!!.openId, member.disUserName, loanInfo.loanName, weiXinService)
            } catch (e: WxErrorException) {
                log.error("给上级发送消息异常", e)
            }
        }

        return Result().ok().put("url", url)
    }

    /**
     * 保存订单记录
     *
     * @param loanOrderVO
     * @param member
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun saveOrder(loanOrderVO: LoanOrderVO, member: DisMemberInfoEntity) {
        val orderInfoRecord = LoanOrderInfoEntity()
        BeanUtils.copyProperties(loanOrderVO, orderInfoRecord)
        orderInfoRecord.id = CommonUtils.uuid
        orderInfoRecord.orderId = AliPayUtils.generateOrderId(loanOrderVO.orderMobile,
                Constant.PayType.applyLoan.value)
        orderInfoRecord.memberId = member.id!!
        orderInfoRecord.orderStatus = 2
        orderInfoRecord.isDelete = 0
        orderInfoRecord.addTime = DateUtils.formatDateTime(LocalDateTime.now())
        loanOrderInfoService.save(orderInfoRecord)
    }

    /**
     * 构造订单通知模板信息
     *
     * @param openId
     * @param name
     * @return
     */
    private fun buildTemplateMsg(openId: String?, name: String?, bankName: String, weiXinService: WeiXinService) {
        val templateDataList = Lists.newArrayList(
                WxMpTemplateData("first", "您收到了一条新的贷款申请订单"),
                WxMpTemplateData("tradeDateTime", DateUtils.formatDateTime(LocalDateTime.now())),
                WxMpTemplateData("orderType", bankName),
                WxMpTemplateData("customerInfo", name),
                WxMpTemplateData("orderItemName", ""),
                WxMpTemplateData("orderItemData", ""),
                WxMpTemplateData("remark", "帮助别人,成就自我")
        )
        WxUtils.buildAndSendTemplateMsg(openId!!, "GB5gLcSDAjHtSxnZxmkcSMd4yU_WEnt2KHhpAZF3_fw",
                templateDataList, weiXinService)
    }


}
