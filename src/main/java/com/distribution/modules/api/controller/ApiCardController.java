package com.distribution.modules.api.controller;

import com.distribution.common.utils.Result;
import com.distribution.modules.api.annotation.AuthIgnore;
import com.distribution.modules.card.entity.CardInfo;
import com.distribution.modules.card.service.CardInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiCardController {
    @Autowired
    private CardInfoService cardInfoService;


    /**
     *  信用卡列表(模糊查詢)
     * @Auther: liuxinxin
     * @Date: 2018/5/26 0:10
     * @Description:
     */
    @AuthIgnore
    @GetMapping("/cardList")
    @ApiOperation(value = "用户银行卡列表")
    public Result cardList(String bankNum, String cardNum, String cardName) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(bankNum)) {
            map.put("bankNum", bankNum);
        }
        if (StringUtils.isNotBlank(cardNum)) {
            map.put("cardNum", cardNum);
        }
        if (StringUtils.isNotBlank(cardName)) {
            map.put("cardName", cardName);
        }
        List<CardInfo> cardInfoList = cardInfoService.queryList(map);
        return Result.ok().put("cardList", cardInfoList);
    }
}
