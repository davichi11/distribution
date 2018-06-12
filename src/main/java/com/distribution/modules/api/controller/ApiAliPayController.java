package com.distribution.modules.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.distribution.ali.pay.AliPayParams;
import com.distribution.ali.pay.AliPayUtils;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.DisMemberInfoService;
import com.distribution.modules.sys.service.SysConfigService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @Description TODO(描述)
 * @create 2018/6/9-16:08
 */
@Slf4j
@Controller
public class ApiAliPayController {
    @Autowired
    private DisMemberInfoService disMemberInfoService;
    @Autowired
    private SysConfigService configService;
    @Value("${risk.url}")
    private String returnUrl;

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
    public ModelAndView checkMemberDisLevel(@RequestParam String mobile, @RequestParam Integer disLevel) {
        ModelAndView modelAndView = new ModelAndView();
        DisMemberInfoEntity disMemberInfoEntity = disMemberInfoService.queryByMobile(mobile);
        //非会员可以通过
        if ("1".equals(disMemberInfoEntity.getDisUserType())) {
            //如果是1级会员
            if (1 == disMemberInfoEntity.getDisLevel()) {
                modelAndView.addObject("您已是1级会员，无需升级");
                return modelAndView;
            }
            //如果是2级会员
            else if (2 == disMemberInfoEntity.getDisLevel()) {
                if (1 != disLevel) {
                    modelAndView.addObject("您已是2级会员，无需升级");
                    return modelAndView;
                }
            }
            //如果是3级会员
            else if (3 == disMemberInfoEntity.getDisLevel()) {
                if (3 == disLevel) {
                    return modelAndView.addObject("您已是3级会员，无需升级");
                }
            }
        }
        //获取会员升级价格配置
        String price = configService.getValue("level_price", "");
        JSONObject priceJson = JSON.parseObject(price);
        String amount = priceJson.getString("level_" + disLevel);
        //校验通过 生成APP支付订单
        String orderNo = AliPayUtils.generateOrderId(mobile, "1");
        log.info("订单号={}",orderNo);
        //构造支付请求参数
        AliPayParams payParams = new AliPayParams();
        payParams.setSubject("会员升级服务");
        payParams.setOutTradeNo(orderNo);
        payParams.setTotalAmount(NumberUtils.toDouble(amount));
//        payParams.setQuitUrl("http://www.qiandaoshou.cn/");
        payParams.setProductCode("QUICK_WAP_WAY");
        try {
            payParams.setPassbackParams(URLEncoder.encode(disLevel.toString(), "UTF-8"));
            modelAndView.addObject("form", AliPayUtils.tradeWapPay(payParams));
            modelAndView.setViewName("pay");
            return modelAndView;
        } catch (Exception e) {
            log.error("生成APP支付订单异常", e);
            return modelAndView.addObject("生成APP支付订单异常");
        }
    }

}
