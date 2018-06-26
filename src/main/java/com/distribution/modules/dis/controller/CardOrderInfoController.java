package com.distribution.modules.dis.controller;

import com.distribution.common.utils.Result;
import com.distribution.modules.dis.entity.CardOrderInfoEntity;
import com.distribution.modules.dis.service.CardOrderInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 办卡订单信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */
@Slf4j
@RestController
@RequestMapping("cardorderinfo")
public class CardOrderInfoController {
    @Autowired
    private CardOrderInfoService cardOrderInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cardorderinfo:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<CardOrderInfoEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo(() -> cardOrderInfoService.queryList(params));
        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cardorderinfo:info")
    public Result info(@PathVariable("id") String id) {
        CardOrderInfoEntity CardOrderInfo = cardOrderInfoService.queryObject(id);

        return Result.ok().put("cardOrderInfo", CardOrderInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cardorderinfo:save")
    public Result save(@RequestBody CardOrderInfoEntity CardOrderInfo) {
        try {
            cardOrderInfoService.save(CardOrderInfo);
        } catch (Exception e) {
            log.error("保存异常", e);
            Result.error("保存异常");
        }

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cardorderinfo:update")
    public Result update(@RequestBody CardOrderInfoEntity CardOrderInfo) {
        try {
            cardOrderInfoService.update(CardOrderInfo);
        } catch (Exception e) {
            log.error("修改异常", e);
            Result.error("修改异常");
        }
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cardorderinfo:delete")
    public Result delete(@RequestBody String[] ids) {
        try {
            if (ids.length == 1) {
                cardOrderInfoService.delete(ids[0]);
            } else {
                cardOrderInfoService.deleteBatch(ids);
            }
        } catch (Exception e) {
            log.error("删除异常", e);
            Result.error("删除异常");
        }
        return Result.ok();
    }

}
