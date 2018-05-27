package com.distribution.modules.api.controller;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.card.entity.CardInfo;
import com.distribution.modules.card.service.CardInfoService;
import com.distribution.modules.dis.entity.CardOrderInfoEntity;
import com.distribution.modules.dis.service.CardOrderInfoService;
import com.distribution.modules.dis.vo.CardOrderInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiCardController {
    @Autowired
    private CardInfoService cardInfoService;
    @Autowired
    private CardOrderInfoService cardOrderInfoService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 手机号正则
     */
    private final Pattern phone = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$");
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
    @PostMapping("/cardList")
    @ApiOperation(value = "信用卡列表")
    public Result list(@RequestBody Map<String, Object> params) {
        //查询列表数据
        PageInfo<CardInfo> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> cardInfoService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }

    /**
     * 添加申请人信息
     *
     * @param
     * @return
     * @author liuxinxin
     * @date 10:58
     */
    @PostMapping("/saveCardOrder")
    @ApiOperation(value = "添加申请人信息")
    public Result saveCaedOrderInfo(@RequestBody CardOrderInfoVO cardOrderInfoVO, String captcha) {
        if (StringUtils.isBlank(cardOrderInfoVO.getOrderMobile()) || !phone.matcher(cardOrderInfoVO.getOrderMobile()).matches()) {
            return Result.error("手机号码不正确");
        }
        if (StringUtils.isBlank(cardOrderInfoVO.getOrderIdcardno()) || !idCardNo.matcher(cardOrderInfoVO.getOrderIdcardno()).matches()) {
            return Result.error("身份证号码不正确");
        }
        //根据手机号获取验证码
        String code = redisTemplate.opsForValue().get(cardOrderInfoVO.getOrderMobile());
        if (!captcha.equals(code)) {
            return Result.error("验证码不正确");
        }
        try {
            cardOrderInfoVO.setId(CommonUtils.getUUID());
            cardOrderInfoVO.setOrderId(CommonUtils.getUUID());
            cardOrderInfoVO.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
            cardOrderInfoVO.setOrderStatus(CardOrderInfoVO.OrderStatus.APPLICATION);
            CardOrderInfoEntity cardOrderInfoEntity = modelMapper.map(cardOrderInfoVO, CardOrderInfoEntity.class);
            cardOrderInfoService.save(cardOrderInfoEntity);
        } catch (Exception e) {
            return Result.error("申请异常");
        }
        return Result.ok();
    }
}
