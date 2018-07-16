package com.distribution.modules.dis.controller

import com.distribution.common.excel.ExportExcel
import com.distribution.common.utils.Result
import com.distribution.modules.dis.entity.CardOrderInfoEntity
import com.distribution.modules.dis.service.CardOrderInfoService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.io.IOException
import javax.servlet.http.HttpServletResponse


/**
 * 办卡订单信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */

@Controller
@RequestMapping("cardorderinfo")
class CardOrderInfoController {
    @Autowired
    private lateinit var cardOrderInfoService: CardOrderInfoService
    private val log = LoggerFactory.getLogger(CardOrderInfoController::class.java)


    /**
     * 列表
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("cardorderinfo:list")
    fun list(@RequestParam params: Map<String, Any>): Result? {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<CardOrderInfoEntity> { cardOrderInfoService.queryList(params) }

        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @ResponseBody
    @RequiresPermissions("cardorderinfo:info")
    fun info(@PathVariable("id") id: String): Result? {
        val cardOrderInfo = cardOrderInfoService.queryObject(id)
        return Result().ok().put("cardOrderInfo", cardOrderInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    @RequiresPermissions("cardorderinfo:save")
    fun save(@RequestBody cardOrderInfo: CardOrderInfoEntity): Result {
        try {
            cardOrderInfoService.save(cardOrderInfo)
        } catch (e: Exception) {
            log.error("保存异常", e)
            Result().error(msg = "保存异常")
        }

        return Result().ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("cardorderinfo:update")
    fun update(@RequestBody cardOrderInfo: CardOrderInfoEntity): Result {
        try {
            cardOrderInfoService.update(cardOrderInfo)
        } catch (e: Exception) {
            log.error("修改异常", e)
            Result().error(msg = "修改异常")
        }

        return Result().ok()
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
    fun statusUpdate(@RequestBody param: Map<String, Any>): Result {
        try {
            cardOrderInfoService.statusUpdate(param)
        } catch (e: Exception) {
            log.error("========", e)
            return Result().error(msg = "订单状态修改失败")
        }

        return Result().ok()
    }

    @RequestMapping("/exportExcel")
    fun exportExcel(@RequestParam params: Map<String, Any>, response: HttpServletResponse) {
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<CardOrderInfoEntity> { cardOrderInfoService.queryList(params) }
        try {
            ({ obj: CardOrderInfoEntity, row: Row ->
                row.createCell(0, CellType.STRING).setCellValue(obj.orderName)
                row.createCell(1, CellType.STRING).setCellValue(obj.orderMobile)
                row.createCell(2, CellType.STRING).setCellValue(obj.orderIdcardno)
                row.createCell(3, CellType.STRING).setCellValue(obj.cardInfo!!.cardName)
                row.createCell(4, CellType.STRING).setCellValue(obj.addTime)
                row.createCell(5, CellType.STRING).setCellValue(obj.cardInfo!!.bankNum)
                var status = ""
                when (obj.orderStatus) {
                    0 -> status = "失败"
                    1 -> status = "成功"
                    2 -> status = "申请中"
                }
                row.createCell(6, CellType.STRING).setCellValue(status)
            } as ExportExcel<CardOrderInfoEntity>).exportExcel(response, pageInfo.list,
                    arrayOf("申请人姓名", "申请人手机号", "申请人身份证", "申请信用卡名称", "申请时间", "申请信用卡编号", "申请状态"),
                    "订单数据")
        } catch (e: IOException) {
            log.error("导出异常", e)
        }

    }
}
