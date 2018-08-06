package com.distribution.modules.api.controller

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
import com.distribution.pojo.Tables
import com.distribution.pojo.Tables.LOAN_ORDER_INFO
import com.distribution.pojo.tables.pojos.LoanInfo
import com.github.pagehelper.PageHelper
import com.google.common.collect.Lists
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import kotlinx.coroutines.experimental.launch
import me.chanjar.weixin.common.exception.WxErrorException
import me.chanjar.weixin.mp.api.WxMpService
import me.chanjar.weixin.mp.bean.result.WxMpUser
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage
import org.apache.commons.lang3.StringUtils
import org.jooq.DSLContext
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
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
    private lateinit var create: DSLContext
    @Autowired
    private lateinit var loanInfoService: LoanInfoService
    @Autowired
    private lateinit var loanOrderInfoService: LoanOrderInfoService
    @Autowired
    private lateinit var disMemberInfoService: DisMemberInfoService
    @Autowired
    private lateinit var wxMpService: WxMpService
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
                    create.selectFrom(Tables.LOAN_INFO).fetchInto(LoanInfo::class.java))
            else -> Result().ok().put("loanInfo", create.selectFrom(Tables.LOAN_INFO).where(Tables.LOAN_INFO.ID.eq(id))
                    .fetchInto(LoanInfo::class.java))
        }
    }

    @GetMapping("/loanOrder/{mobile}")
    @ApiOperation("查询用户贷款订单记录")
    @ApiImplicitParam(paramType = "patch", dataType = "string", name = "用户手机号", value = "mobile")
    fun getLoanOrderInfo(@PathVariable("mobile") mobile: String, page: Int = 0, limit: Int = 0, status: Int = 0): Result {
        val member = disMemberInfoService.queryByMobile(mobile)!!
        val memberIds = member.disMemberChildren!!.map { it.id }.toMutableList()
        memberIds.add(member.id)
        val param = HashMap<String, Any>()
        param["memberIds"] = memberIds
        param["status"] = status
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
        val loanInfo = create.selectFrom(Tables.LOAN_INFO)
                .where(Tables.LOAN_INFO.ID.eq(loanOrder.loanId)).fetchOne()
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
        val count = create.selectCount().from(LOAN_ORDER_INFO)
                .where(Tables.LOAN_ORDER_INFO.ORDER_MOBILE.eq(loanOrder.orderMobile))
                .fetchOneInto(Int::class.javaPrimitiveType)
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
        launch {
            //发送订单信息提醒
            val message = buildTemplateMsg(member.openId, member.disUserName, loanInfo.loanName)
            try {
                wxMpService.templateMsgService.sendTemplateMsg(message)
            } catch (e: WxErrorException) {
                log.error("发送订单信息提醒异常", e)
            }

            //给上级发送消息
            val parentMessage = buildTemplateMsg(member.disMemberParent!!.openId, member.disUserName, loanInfo.loanName)
            try {
                wxMpService.templateMsgService.sendTemplateMsg(parentMessage)
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
        val orderInfoRecord = create.newRecord(Tables.LOAN_ORDER_INFO)
        BeanUtils.copyProperties(loanOrderVO, orderInfoRecord)
        orderInfoRecord.id = CommonUtils.uuid
        orderInfoRecord.orderId = AliPayUtils.generateOrderId(loanOrderVO.orderMobile,
                Constant.PayType.applyLoan.value)
        orderInfoRecord.memberId = member.id
        orderInfoRecord.orderStatus = "2"
        orderInfoRecord.isDelete = 0
        orderInfoRecord.addTime = DateUtils.formatDateTime(LocalDateTime.now())
        orderInfoRecord.insert()
    }

    /**
     * 构造订单通知模板信息
     *
     * @param openId
     * @param name
     * @return
     */
    private fun buildTemplateMsg(openId: String?, name: String?, bankName: String): WxMpTemplateMessage {
        val wxMpTemplateMessage = WxMpTemplateMessage()
        wxMpTemplateMessage.templateId = "GB5gLcSDAjHtSxnZxmkcSMd4yU_WEnt2KHhpAZF3_fw"
        wxMpTemplateMessage.toUser = openId
        val templateDataList = Lists.newArrayList(
                WxMpTemplateData("first", "您收到了一条新的贷款申请订单"),
                WxMpTemplateData("tradeDateTime", DateUtils.formatDateTime(LocalDateTime.now())),
                WxMpTemplateData("orderType", bankName),
                WxMpTemplateData("customerInfo", name),
                WxMpTemplateData("orderItemName", ""),
                WxMpTemplateData("orderItemData", ""),
                WxMpTemplateData("remark", "帮助别人,成就自我")
        )
        wxMpTemplateMessage.data = templateDataList
        return wxMpTemplateMessage
    }


}