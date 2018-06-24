package com.distribution.modules.dis.controller;

import com.distribution.common.excel.ExportExcel;
import com.distribution.common.utils.Result;
import com.distribution.modules.dis.entity.CardOrderInfoEntity;
import com.distribution.modules.dis.service.CardOrderInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * 办卡订单信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */
@Slf4j
@Controller
@RequestMapping("cardorderinfo")
public class CardOrderInfoController {
    @Autowired
    private CardOrderInfoService cardOrderInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("cardorderinfo:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        PageInfo<CardOrderInfoEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo(() -> cardOrderInfoService.queryList(params));

        return Result.ok().put("page", pageInfo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @ResponseBody
    @RequiresPermissions("cardorderinfo:info")
    public Result info(@PathVariable("id") String id) {
        CardOrderInfoEntity cardOrderInfo = cardOrderInfoService.queryObject(id);
        return Result.ok().put("cardOrderInfo", cardOrderInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    @RequiresPermissions("cardorderinfo:save")
    public Result save(@RequestBody CardOrderInfoEntity cardOrderInfo) {
        try {
            cardOrderInfoService.save(cardOrderInfo);
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
    @ResponseBody
    @RequiresPermissions("cardorderinfo:update")
    public Result update(@RequestBody CardOrderInfoEntity cardOrderInfo) {
        try {
            cardOrderInfoService.update(cardOrderInfo);
        } catch (Exception e) {
            log.error("修改异常", e);
            Result.error("修改异常");
        }
        return Result.ok();
    }

    /**
     * 修改订单状态
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/27 12:39
     * @Description:
     */
    @PutMapping("/statusUpdate")
    @ResponseBody
    public Result statusUpdate(@RequestBody Map<String, Object> param) {
        try {
            cardOrderInfoService.statusUpdate(param);
        } catch (Exception e) {
            log.error("========", e);
            return Result.error("订单状态修改失败");
        }
        return Result.ok();
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        PageInfo<CardOrderInfoEntity> pageInfo = PageHelper.startPage(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo(() -> cardOrderInfoService.queryList(params));
        try {
            ((ExportExcel<CardOrderInfoEntity>) (obj, row) -> {
                row.createCell(0, CellType.STRING).setCellValue(obj.getOrderName());
                row.createCell(1, CellType.STRING).setCellValue(obj.getOrderMobile());
                row.createCell(2, CellType.STRING).setCellValue(obj.getOrderIdcardno());
                row.createCell(3, CellType.STRING).setCellValue(obj.getCardInfo().getCardName());
                row.createCell(4, CellType.STRING).setCellValue(obj.getAddTime());
                row.createCell(5, CellType.STRING).setCellValue(obj.getCardInfo().getBankNum());
                String status = "";
                switch (obj.getOrderStatus()) {
                    case 0:
                        status = "失败";
                        break;
                    case 1:
                        status = "成功";
                        break;
                    case 2:
                        status = "申请中";
                        break;
                    default:

                }
                row.createCell(6, CellType.STRING).setCellValue(status);
            }).exportExcel(response, pageInfo.getList(),
                    new String[]{"申请人姓名", "申请人手机号", "申请人身份证", "申请信用卡名称", "申请时间", "申请信用卡编号", "申请状态"},
                    "订单数据");
        } catch (IOException e) {
            log.error("导出异常", e);
        }
    }
}
