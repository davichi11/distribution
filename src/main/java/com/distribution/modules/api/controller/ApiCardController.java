package com.distribution.modules.api.controller;

import com.distribution.ali.pay.AliPayUtils;
import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.Constant;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.card.entity.CardInfo;
import com.distribution.modules.card.service.CardInfoService;
import com.distribution.modules.dis.entity.CardOrderInfoEntity;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.CardOrderInfoService;
import com.distribution.modules.dis.service.DisMemberInfoService;
import com.distribution.modules.dis.vo.CardOrderInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiCardController {
    @Autowired
    private CardInfoService cardInfoService;
    @Autowired
    private CardOrderInfoService cardOrderInfoService;
    @Autowired
    private DisMemberInfoService disMemberInfoService;
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 手机号正则
     */
//    private final Pattern phone = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$");
    /**
     * 身份证正则
     */
    private final Pattern idCardNo = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");

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
    public Result list(String page, String limit) {
        //查询列表数据
        PageInfo<CardInfo> pageInfo = PageHelper.startPage(NumberUtils.toInt(page, 0), NumberUtils.toInt(limit, 0))
                .doSelectPageInfo(() -> cardInfoService.queryList(null));
        return Result.ok().put("page", pageInfo);
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
    public Result memberCardList(@PathVariable String mobile, String page, String limit, String type) {
        PageInfo<CardOrderInfoEntity> pageInfo = null;
        DisMemberInfoEntity member = disMemberInfoService.queryByMobile(mobile);
        if (member != null) {
            List<String> memberIds = member.getDisMemberChildren().stream().filter(Objects::nonNull)
                    .map(DisMemberInfoEntity::getId).collect(Collectors.toList());
            memberIds.add(member.getId());

            Map<String, Object> param = new HashMap<>(4);
            param.put("memberIds", memberIds);
            param.put("orderStatus", type);
            pageInfo = PageHelper.startPage(NumberUtils.toInt(page, 0), NumberUtils.toInt(limit, 0))
                    .doSelectPageInfo(() -> cardOrderInfoService.queryList(param));
            //模糊手机号和身份证
            pageInfo.getList().forEach(order -> {
                order.setOrderIdcardno(CommonUtils.fuzzyIdCode(order.getOrderIdcardno()));
                order.setOrderMobile(CommonUtils.fuzzyMobile(order.getOrderMobile()));
            });
        }
        return Result.ok().put("memberCardList", pageInfo);
    }

    @ApiOperation(value = "业绩总数,信用卡,贷款,积分 数量")
    @GetMapping("/allCount/{mobile}")
    public Result getAllCount(@PathVariable("mobile") String mobile) {
        DisMemberInfoEntity member = disMemberInfoService.queryByMobile(mobile);
        int allCount = 0;
        int cardCount = 0;
        int loanCount = 0;
        int inteCount = 0;
        if (member != null) {
            List<String> memberIds = member.getDisMemberChildren().stream().filter(Objects::nonNull)
                    .map(DisMemberInfoEntity::getId).collect(Collectors.toList());
            memberIds.add(member.getId());
            Map<String, Object> param = new HashMap<>(4);
            param.put("memberIds", memberIds);
            param.put("orderStatus", "1");
            allCount = cardCount = cardOrderInfoService.queryList(param).size();
        }
        return Result.ok().put("allCount", allCount).put("cardCount", cardCount).put("loanCount", loanCount)
                .put("inteCount", inteCount);
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
    public Result saveCardOrderInfo(@RequestBody CardOrderInfoVO cardOrderInfoVO) {
        if (StringUtils.isBlank(cardOrderInfoVO.getOrderMobile())) {
            return Result.error("手机号码不正确");
        }
        if (StringUtils.isBlank(cardOrderInfoVO.getOrderIdcardno()) || !idCardNo.matcher(cardOrderInfoVO.getOrderIdcardno()).matches()) {
            return Result.error("身份证号码不正确");
        }
        if (StringUtils.isBlank(cardOrderInfoVO.getBankNum()) || StringUtils.isBlank(cardOrderInfoVO.getCardNum())) {
            return Result.error("银行代号不能为空");
        }
        CardInfo cardInfo = cardInfoService.queryByBankNum(cardOrderInfoVO.getBankNum());
        try {
            DisMemberInfoEntity member = disMemberInfoService.queryByMobile(cardOrderInfoVO.getOrderMobile());
            //先调用第三方接口保存用户信息并返回url
            String url = cardInfoService.getProductUrl(member, cardOrderInfoVO.getBankNum());
            if (StringUtils.isNotBlank(url)) {
                CardOrderInfoEntity cardOrderInfoEntity = modelMapper.map(cardOrderInfoVO, CardOrderInfoEntity.class);
                cardOrderInfoEntity.setId(CommonUtils.getUUID());
                cardOrderInfoEntity.setOrderId(AliPayUtils.generateOrderId(cardOrderInfoVO.getOrderMobile(),
                        Constant.PayType.applyCard.getValue()));
                cardOrderInfoEntity.setMemberInfo(member);
                cardOrderInfoEntity.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
                cardOrderInfoEntity.setOrderStatus(CardOrderInfoVO.OrderStatus.APPLICATION);
                cardOrderInfoEntity.setCardInfo(cardInfo);
                cardOrderInfoEntity.setIsDelete("1");
                cardOrderInfoService.save(cardOrderInfoEntity);
                //发送订单信息提醒
                WxMpTemplateMessage message = buildTemplateMsg(member.getOpenId(), member.getDisUserName(),
                        cardInfo.getCardName(), "", "");
                wxMpService.getTemplateMsgService().sendTemplateMsg(message);

                //给上级发送消息
                WxMpTemplateMessage parentMessage = buildTemplateMsg(member.getDisMemberParent().getOpenId(),
                        member.getDisUserName(), cardInfo.getCardName(), "", "");
                wxMpService.getTemplateMsgService().sendTemplateMsg(parentMessage);
            }
            return Result.ok().put("url", url);
        } catch (Exception e) {
            log.error("申请异常", e);
            return Result.error("申请异常");
        }
    }


    /**
     * 构造订单通知模板信息
     *
     * @param openId
     * @param name
     * @return
     */
    private WxMpTemplateMessage buildTemplateMsg(String openId, String name, String bankName,
                                                 String orderItemName, String orderItemData) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("GB5gLcSDAjHtSxnZxmkcSMd4yU_WEnt2KHhpAZF3_fw");
        wxMpTemplateMessage.setToUser(openId);
        List<WxMpTemplateData> templateDataList = Lists.newArrayList(
                new WxMpTemplateData("first", "您收到了一条新的订单"),
                new WxMpTemplateData("tradeDateTime", DateUtils.formatDateTime(LocalDateTime.now())),
                new WxMpTemplateData("orderType", bankName),
                new WxMpTemplateData("customerInfo", name),
                new WxMpTemplateData("orderItemName", orderItemName),
                new WxMpTemplateData("orderItemData", orderItemData),
                new WxMpTemplateData("remark", "帮助别人,成就自我")
        );
        wxMpTemplateMessage.setData(templateDataList);
        return wxMpTemplateMessage;
    }
}
