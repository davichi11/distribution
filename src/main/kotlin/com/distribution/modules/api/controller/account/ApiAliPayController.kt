package com.distribution.modules.api.controller.account

import com.alibaba.fastjson.JSON
import com.distribution.ali.pay.AliPayParams
import com.distribution.ali.pay.AliPayUtils
import com.distribution.modules.account.service.MemberAccountService
import com.distribution.modules.dis.entity.OrderHistory
import com.distribution.modules.dis.service.DisMemberInfoService
import com.distribution.modules.dis.service.OrderHistoryService
import com.distribution.modules.sys.service.SysConfigService
import io.swagger.annotations.ApiOperation
import org.apache.commons.lang3.math.NumberUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.net.URLEncoder
import java.time.LocalDateTime

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @create 2018/6/9-16:08
 */

@Controller
@CrossOrigin
class ApiAliPayController {
    @Autowired
    private lateinit var disMemberInfoService: DisMemberInfoService
    @Autowired
    private lateinit var accountService: MemberAccountService
    @Autowired
    private lateinit var configService: SysConfigService
    @Autowired
    private lateinit var orderHistoryService: OrderHistoryService
    private val log = LoggerFactory.getLogger(ApiAliPayController::class.java)

    /**
     * 购买会员
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 14:25
     */
    @GetMapping("pay")
    @ApiOperation(value = "购买会员")
    fun checkMemberDisLevel(@RequestParam mobile: String, @RequestParam disLevel: Int?): ModelAndView {
        val modelAndView = ModelAndView()
        val disMemberInfoEntity = disMemberInfoService.queryByMobile(mobile)
        //非会员可以通过
        if ("1" == disMemberInfoEntity!!.disUserType) {
            //如果是1级会员
            if (1 == disMemberInfoEntity.disLevel) {
                modelAndView.addObject("您已是1级会员，无需升级")
                return modelAndView
            } else if (2 == disMemberInfoEntity.disLevel) {
                if (1 != disLevel) {
                    modelAndView.addObject("您已是2级会员，无需升级")
                    return modelAndView
                }
            } else if (3 == disMemberInfoEntity.disLevel) {
                if (3 == disLevel) {
                    return modelAndView.addObject("您已是3级会员，无需升级")
                }
            }//如果是3级会员
            //如果是2级会员
        }
        //获取会员升级价格配置
        val price = configService.getValue("level_price", "")
        val priceJson = JSON.parseObject(price)
        val amount = priceJson.getString("level_$disLevel")
        //校验通过 生成APP支付订单
        val orderNo = AliPayUtils.generateOrderId(mobile, "1")
        log.info("订单号={}", orderNo)
        //构造支付请求参数
        val payParams = AliPayParams()
        payParams.subject = "会员升级服务"
        payParams.outTradeNo = orderNo
        payParams.totalAmount = NumberUtils.toDouble(amount)
        //        payParams.setQuitUrl("http://www.qiandaoshou.cn/");
        payParams.productCode = "QUICK_WAP_WAY"

        //新增订单记录
        val orderHistory = OrderHistory()
        orderHistory.orderId = orderNo
        orderHistory.mobile = mobile
        orderHistory.amount = NumberUtils.toDouble(amount)
        orderHistory.orderStatus = 2
        orderHistory.addTime = LocalDateTime.now()
        orderHistory.finishTime = LocalDateTime.now()
        return try {
            val account = accountService.selectMemberAccountByUserId(disMemberInfoEntity.id!!)
            if (account != null) {
                val aliPayAccount = account.aliPayAccount
                orderHistory.account = aliPayAccount
            }
            orderHistoryService.save(orderHistory)
            payParams.passbackParams = URLEncoder.encode(disLevel.toString(), "UTF-8")
            modelAndView.addObject("form", AliPayUtils.tradeWapPay(payParams))
            modelAndView.viewName = "pay"
            modelAndView
        } catch (e: Exception) {
            log.error("生成APP支付订单异常", e)
            modelAndView.addObject("生成APP支付订单异常")
        }

    }

}
