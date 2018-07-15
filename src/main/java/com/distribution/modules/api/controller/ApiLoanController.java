package com.distribution.modules.api.controller;

import com.distribution.ali.pay.AliPayUtils;
import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.Constant;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.api.pojo.vo.LoanOrderVO;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.entity.LoanOrderInfoEntity;
import com.distribution.modules.dis.service.DisMemberInfoService;
import com.distribution.modules.dis.service.LoanInfoService;
import com.distribution.modules.dis.service.LoanOrderInfoService;
import com.distribution.pojo.Tables;
import com.distribution.pojo.tables.pojos.LoanInfo;
import com.distribution.pojo.tables.records.LoanInfoRecord;
import com.distribution.pojo.tables.records.LoanOrderInfoRecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.distribution.pojo.Tables.LOAN_ORDER_INFO;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @Description TODO(描述)
 * @create 2018/7/6-11:20
 */
@Slf4j
@Api("贷款接口")
@RestController
@RequestMapping("/api")
public class ApiLoanController {
    @Autowired
    private DSLContext create;
    @Autowired
    private LoanInfoService loanInfoService;
    @Autowired
    private LoanOrderInfoService loanOrderInfoService;
    @Autowired
    private DisMemberInfoService disMemberInfoService;
    @Autowired
    private WxMpService wxMpService;
    /**
     * 身份证正则
     */
    private final Pattern idCardNo = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");

    @AuthIgnore
    @GetMapping("/loanInfo")
    @ApiOperation("查询所有贷款详情")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "贷款产品ID", value = "id")
    public Result getLoanInfo(String id) {
        if (StringUtils.isBlank(id)) {
            return Result.ok().put("loanInfos", create.selectFrom(Tables.LOAN_INFO).fetchInto(LoanInfo.class));
        }
        return Result.ok().put("loanInfo", create.selectFrom(Tables.LOAN_INFO).where(Tables.LOAN_INFO.ID.eq(id))
                .fetchInto(LoanInfo.class));
    }

    @GetMapping("/loanOrder/{mobile}")
    @ApiOperation("查询用户贷款订单记录")
    @ApiImplicitParam(paramType = "patch", dataType = "string", name = "用户手机号", value = "mobile")
    public Result getLoanOrderInfo(@PathVariable("mobile") String mobile,Integer page, Integer limit, Integer status) {
        Map<String, Object> param = new HashMap<>();
        param.put("mobile", mobile);
        param.put("status", status);
        //查询列表数据
        PageInfo<LoanOrderInfoEntity> pageInfo = PageHelper.startPage(page, limit)
                .doSelectPageInfo(() -> loanOrderInfoService.queryList(param));
        return Result.ok().put("loanOrder", pageInfo);
    }

    @PostMapping("/loan")
    @ApiOperation("用户贷款申请")
    @ApiImplicitParam(paramType = "body", dataType = "LoanOrderVO")
    public Result applyLoan(@RequestBody LoanOrderVO loanOrder) {
        if (StringUtils.isBlank(loanOrder.getOrderMobile())) {
            return Result.error("手机号码不正确");
        }
        if (StringUtils.isBlank(loanOrder.getOrderIdcardno()) ||
                !idCardNo.matcher(loanOrder.getOrderIdcardno()).matches()) {
            return Result.error("身份证号码不正确");
        }
        if (StringUtils.isBlank(loanOrder.getLoanId())) {
            return Result.error("银行代号不能为空");
        }
        LoanInfoRecord loanInfo = create.selectFrom(Tables.LOAN_INFO)
                .where(Tables.LOAN_INFO.ID.eq(loanOrder.getLoanId())).fetchOne();
        DisMemberInfoEntity member = disMemberInfoService.queryByMobile(loanOrder.getOrderMobile());
        //先调用第三方接口保存用户信息并返回url
        String url;
        try {
            url = loanInfoService.getProductUrl(member, loanOrder.getLoanId());
        } catch (Exception e) {
            log.error("申请异常",e);
            return Result.error("申请异常");
        }
        if (StringUtils.isBlank(url)) {
            return Result.error("申请异常");
        }
        //查看是否已办过该贷款产品
        Integer count = create.selectCount().from(LOAN_ORDER_INFO).where(LOAN_ORDER_INFO.ORDER_MOBILE.eq(loanOrder.getOrderMobile()))
                .fetchOneInto(int.class);
        if (count >= 1) {
            return Result.ok().put("url", url);
        }

        try {
            saveOrder(loanOrder, member);
        } catch (Exception e) {
            log.error("保存订单异常", e);
            return Result.error("保存订单异常");
        }

        //发送消息前先查询是否已关注
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxMpService.getUserService().userInfo(member.getOpenId(), "zh_CN");
        } catch (WxErrorException e) {
            log.error("查询关注者异常", e);
        }
        if (wxMpUser == null) {
            return Result.ok().put("url", url);
        }
        //发送订单信息提醒
        WxMpTemplateMessage message = buildTemplateMsg(member.getOpenId(), member.getDisUserName(), loanInfo.getLoanName());
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(message);
        } catch (WxErrorException e) {
            log.error("发送订单信息提醒异常", e);
        }
        //给上级发送消息
        WxMpTemplateMessage parentMessage = buildTemplateMsg(member.getDisMemberParent().getOpenId(),
                member.getDisUserName(), loanInfo.getLoanName());
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(parentMessage);
        } catch (WxErrorException e) {
            log.error("给上级发送消息异常", e);
        }
        return Result.ok().put("url", url);
    }

    /**
     * 保存订单记录
     *
     * @param loanOrderVO
     * @param member
     * @throws Exception
     */
    private void saveOrder(LoanOrderVO loanOrderVO, DisMemberInfoEntity member) throws Exception {
        LoanOrderInfoRecord orderInfoRecord = create.newRecord(LOAN_ORDER_INFO);
        BeanUtils.copyProperties(loanOrderVO, orderInfoRecord);
        orderInfoRecord.setId(CommonUtils.getUUID());
        orderInfoRecord.setOrderId(AliPayUtils.generateOrderId(loanOrderVO.getOrderMobile(),
                Constant.PayType.applyLoan.getValue()));
        orderInfoRecord.setMemberId(member.getId());
        orderInfoRecord.setOrderStatus("2");
        orderInfoRecord.setIsDelete(false);
        orderInfoRecord.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
        orderInfoRecord.insert();
    }

    /**
     * 构造订单通知模板信息
     *
     * @param openId
     * @param name
     * @return
     */
    private WxMpTemplateMessage buildTemplateMsg(String openId, String name, String bankName) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("GB5gLcSDAjHtSxnZxmkcSMd4yU_WEnt2KHhpAZF3_fw");
        wxMpTemplateMessage.setToUser(openId);
        List<WxMpTemplateData> templateDataList = Lists.newArrayList(
                new WxMpTemplateData("first", "您收到了一条新的贷款申请订单"),
                new WxMpTemplateData("tradeDateTime", DateUtils.formatDateTime(LocalDateTime.now())),
                new WxMpTemplateData("orderType", bankName),
                new WxMpTemplateData("customerInfo", name),
                new WxMpTemplateData("orderItemName", ""),
                new WxMpTemplateData("orderItemData", ""),
                new WxMpTemplateData("remark", "帮助别人,成就自我")
        );
        wxMpTemplateMessage.setData(templateDataList);
        return wxMpTemplateMessage;
    }


}
