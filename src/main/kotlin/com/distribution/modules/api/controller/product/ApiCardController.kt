package com.distribution.modules.api.controller.product

import com.distribution.ali.pay.AliPayUtils
import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.Constant
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.card.entity.CardInfo
import com.distribution.modules.card.service.CardInfoService
import com.distribution.modules.dis.entity.CardOrderInfoEntity
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.service.CardOrderInfoService
import com.distribution.modules.dis.service.DisMemberInfoService
import com.distribution.modules.dis.vo.CardOrderInfoVO
import com.distribution.pojo.Tables.INTEGRAL_ORDER
import com.distribution.pojo.Tables.LOAN_ORDER_INFO
import com.distribution.weixin.service.WeiXinService
import com.distribution.weixin.utils.WxUtils
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.google.common.collect.Lists
import io.swagger.annotations.ApiOperation
import kotlinx.coroutines.experimental.launch
import me.chanjar.weixin.mp.api.WxMpService
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.jooq.DSLContext
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern


@RestController
@RequestMapping("/api")
@CrossOrigin
class ApiCardController {
    @Autowired
    private lateinit var cardInfoService: CardInfoService
    @Autowired
    private lateinit var cardOrderInfoService: CardOrderInfoService
    @Autowired
    private lateinit var disMemberInfoService: DisMemberInfoService
    @Autowired
    private lateinit var wxMpService: WxMpService
    @Autowired
    private lateinit var weiXinService: WeiXinService
    @Autowired
    private lateinit var modelMapper: ModelMapper
    @Autowired
    private lateinit var create: DSLContext

    /**
     * 手机号正则
     */
    //    private final Pattern phone = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$");
    /**
     * 身份证正则
     */
    private val idCardNo = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$")

    private val log = LoggerFactory.getLogger(ApiCardController::class.java)

    /**
     * 信用卡列表(模糊查詢)
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/26 0:10
     * @Description:
     */
    @AuthIgnore
    @GetMapping("/cardList")
    @ApiOperation(value = "信用卡列表")
    fun list(@RequestParam(name = "page", defaultValue = "0") page: String = "0", @RequestParam(name = "limit", defaultValue = "10") limit: String = "10"): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(NumberUtils.toInt(page, 0), NumberUtils.toInt(limit, 0))
                .doSelectPageInfo<CardInfo> { cardInfoService.queryList(mapOf()) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 用户银行卡列表
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/24 20:40
     * @Description:
     */
    @GetMapping("/memberCardList/{mobile}")
    @ApiOperation(value = "用户办卡记录,订单明细")
    fun memberCardList(@PathVariable mobile: String, page: String = "0", limit: String = "0", type: String = "0"): Result {
        var pageInfo: PageInfo<CardOrderInfoEntity> = PageInfo()
        val member = disMemberInfoService.queryByMobile(mobile)
        if (member != null) {
            val memberIds = member.disMemberChildren!!.map { it.id }.toMutableList()
            memberIds.add(member.id)

            val param = HashMap<String, Any>(4)
            param["memberIds"] = memberIds
            param["orderStatus"] = type
            pageInfo = PageHelper.startPage<Any>(NumberUtils.toInt(page, 0), NumberUtils.toInt(limit, 0))
                    .doSelectPageInfo { cardOrderInfoService.queryList(param) }
            //模糊手机号和身份证
            pageInfo.list.forEach { order ->
                order.orderIdcardno = CommonUtils.fuzzyIdCode(order.orderIdcardno)
                order.orderMobile = CommonUtils.fuzzyMobile(order.orderMobile)
            }
        }
        return Result().ok().put("memberCardList", pageInfo)
    }

    @ApiOperation(value = "业绩总数,信用卡,贷款,积分 数量")
    @GetMapping("/allCount/{mobile}")
    fun getAllCount(@PathVariable("mobile") mobile: String): Result {
        val member = disMemberInfoService.queryByMobile(mobile)
        var allCount = 0
        var cardCount = 0
        var loanCount = 0
        var inteCount = 0
        if (member != null) {
            val memberIds = member.disMemberChildren!!.map { it.id }.toMutableList()
            memberIds.add(member.id)
            val param = HashMap<String, Any>(4)
            param["memberIds"] = memberIds
            param["orderStatus"] = "1"
            cardCount = cardOrderInfoService.queryList(param).size
            loanCount = create.selectCount().from(LOAN_ORDER_INFO)
                    .where(LOAN_ORDER_INFO.ORDER_MOBILE.eq(mobile))
                    .and(LOAN_ORDER_INFO.ORDER_STATUS.eq("1"))
                    .fetchOneInto(Int::class.javaPrimitiveType)
            inteCount = create.selectCount().from(INTEGRAL_ORDER)
                    .where(INTEGRAL_ORDER.MOBILE.eq(NumberUtils.toLong(mobile)))
                    .and(INTEGRAL_ORDER.STATUS.eq("1"))
                    .fetchOneInto(Int::class.javaPrimitiveType)
            allCount = cardCount + loanCount + inteCount

        }
        return Result().ok().put("allCount", allCount).put("cardCount", cardCount).put("loanCount", loanCount)
                .put("inteCount", inteCount)
    }


    /**
     * 添加申请人信息
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 10:58
     */
    @AuthIgnore
    @PostMapping("/saveCardOrder")
    @ApiOperation(value = "添加申请人信息")
    fun saveCardOrderInfo(@RequestBody cardOrderInfoVO: CardOrderInfoVO): Result {
        if (StringUtils.isBlank(cardOrderInfoVO.orderMobile)) {
            return Result().error(msg = "手机号码不正确")
        }
        if (StringUtils.isBlank(cardOrderInfoVO.orderIdcardno) || !idCardNo.matcher(cardOrderInfoVO.orderIdcardno).matches()) {
            return Result().error(msg = "身份证号码不正确")
        }
        if (StringUtils.isBlank(cardOrderInfoVO.bankNum) || StringUtils.isBlank(cardOrderInfoVO.cardNum)) {
            return Result().error(msg = "银行代号不能为空")
        }
        val cardInfo = cardInfoService.queryByBankNum(cardOrderInfoVO.bankNum)
        try {
            val member = disMemberInfoService.queryByMobile(cardOrderInfoVO.orderMobile)!!
            //先调用第三方接口保存用户信息并返回url
            val url = cardInfoService.getProductUrl(member, cardOrderInfoVO.bankNum)
            if (url.isEmpty()) {
                return Result().error(msg = "申请异常")
            }
            //查询改用户是否已办理过该银行的信用卡,如果有办过就不保存也不提醒
            val countBankNum = cardOrderInfoService.countUserCard(member.id!!, cardOrderInfoVO.bankNum)
            if (countBankNum >= 1) {
                return Result().ok().put("url", url)
            }
            //订单数据保存
            saveCardOrder(cardOrderInfoVO, cardInfo, member)
            //发送消息前先查询是否已关注
            wxMpService.userService.userInfo(member.openId, "zh_CN") ?: return Result().ok().put("url", url)
            launch {
                //发送订单信息提醒
                val message = buildTemplateMsg(member.disUserName!!, cardInfo.cardName, "", "")
                WxUtils.buildAndSendTemplateMsg(member.openId!!, "GB5gLcSDAjHtSxnZxmkcSMd4yU_WEnt2KHhpAZF3_fw",
                        message, weiXinService)
                //给上级发送消息
                val parentMessage = buildTemplateMsg(member.disUserName!!, cardInfo.cardName, "", "")
                WxUtils.buildAndSendTemplateMsg(member.disMemberParent!!.openId!!, "GB5gLcSDAjHtSxnZxmkcSMd4yU_WEnt2KHhpAZF3_fw",
                        parentMessage, weiXinService)
            }
            return Result().ok().put("url", url)
        } catch (e: Exception) {
            log.error("申请异常", e)
            return Result().error(msg = "申请异常")
        }

    }

    /**
     * 保存订单数据
     *
     * @param cardOrderInfoVO
     * @param cardInfo
     * @param member
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun saveCardOrder(cardOrderInfoVO: CardOrderInfoVO, cardInfo: CardInfo, member: DisMemberInfoEntity) {
        val cardOrderInfoEntity = modelMapper.map(cardOrderInfoVO, CardOrderInfoEntity::class.java)
        cardOrderInfoEntity.id = CommonUtils.uuid
        cardOrderInfoEntity.orderId = AliPayUtils.generateOrderId(cardOrderInfoVO.orderMobile,
                Constant.PayType.applyCard.value)
        cardOrderInfoEntity.memberInfo = member
        cardOrderInfoEntity.addTime = DateUtils.formatDateTime(LocalDateTime.now())
        cardOrderInfoEntity.orderStatus = CardOrderInfoVO.OrderStatus.APPLICATION
        cardOrderInfoEntity.cardInfo = cardInfo
        cardOrderInfoEntity.isDelete = "1"
        cardOrderInfoService.save(cardOrderInfoEntity)
    }


    /**
     * 构造订单通知模板信息
     *
     * @param name
     * @return
     */
    private fun buildTemplateMsg(name: String, bankName: String, orderItemName: String,
                                 orderItemData: String): List<WxMpTemplateData> {
        return Lists.newArrayList(
                WxMpTemplateData("first", "您收到了一条新的信用卡订单"),
                WxMpTemplateData("tradeDateTime", DateUtils.formatDateTime(LocalDateTime.now())),
                WxMpTemplateData("orderType", bankName),
                WxMpTemplateData("customerInfo", name),
                WxMpTemplateData("orderItemName", orderItemName),
                WxMpTemplateData("orderItemData", orderItemData),
                WxMpTemplateData("remark", "帮助别人,成就自我")
        )
    }

}
