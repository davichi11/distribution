package com.distribution.modules.card.controller;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.common.utils.Result;
import com.distribution.modules.card.entity.CardInfo;
import com.distribution.modules.card.service.CardInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;


/**
 * 可办信用卡信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */
@Slf4j
@RestController
@RequestMapping("cardinfo")
public class CardInfoController {
    @Autowired
    private CardInfoService cardInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cardinfo:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<CardInfo> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo(() -> cardInfoService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cardinfo:info")
    public Result info(@PathVariable("id") String id) {
        CardInfo cardInfo = cardInfoService.queryObject(id);

        return Result.ok().put("cardInfo", cardInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cardinfo:save")
    public Result save(@RequestBody CardInfo cardInfo) {
        try {
            cardInfo.setId(CommonUtils.getUUID());
            cardInfo.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
            cardInfoService.save(cardInfo);
        } catch (Exception e) {
            log.error("保存异常", e);
           return Result.error("保存异常");
        }

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cardinfo:update")
    public Result update(@RequestBody CardInfo cardInfo) {
        try {
            cardInfo.setUpdateTime(DateUtils.formatDateTime(LocalDateTime.now()));
            cardInfoService.update(cardInfo);
        } catch (Exception e) {
            log.error("修改异常", e);
           return Result.error("修改异常");
        }
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cardinfo:delete")
    public Result delete(@RequestBody String[] ids) {
        try {
            if (ids.length == 1) {
                cardInfoService.delete(ids[0]);
            } else {
                cardInfoService.deleteBatch(ids);
            }
        } catch (Exception e) {
            log.error("删除信用卡信息异常", e);
           return Result.error("删除信用卡信息异常");
        }
        return Result.ok();
    }

}
