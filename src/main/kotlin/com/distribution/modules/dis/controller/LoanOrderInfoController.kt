package com.distribution.modules.dis.controller

import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.dis.entity.LoanOrderInfoEntity
import com.distribution.modules.dis.service.LoanOrderInfoService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


/**
 * 贷款订单
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */

@RestController
@RequestMapping("loanorderinfo")
class LoanOrderInfoController {
    @Autowired
    private lateinit var loanOrderInfoService: LoanOrderInfoService
    private val log = LoggerFactory.getLogger(LoanOrderInfoController::class.java)

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("loanorderinfo:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo<LoanOrderInfoEntity> { loanOrderInfoService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("loanorderinfo:info")
    fun info(@PathVariable("id") id: String): Result {
        val loanOrderInfo = loanOrderInfoService.queryObject(id)

        return Result().ok().put("loanOrderInfo", loanOrderInfo)
    }

    @RequestMapping("/fenrunmoney")
    @RequiresPermissions("loanorderinfo:update")
    fun huikuan(applyforid: String, fenrunmoney: Double): Result {
        val loanOrderInfo = loanOrderInfoService.queryObject(applyforid)
        try {
            loanOrderInfo.orderStatus = 1
            loanOrderInfo.loanMoney = fenrunmoney
            loanOrderInfo.updateTime = DateUtils.formatDateTime(LocalDateTime.now())
            loanOrderInfoService.update(loanOrderInfo)
        } catch (e: Exception) {
            log.error("分润异常", e)
            return Result().error(msg = "分润异常")
        }

        return Result().ok()
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("loanorderinfo:save")
    fun save(@RequestBody loanOrderInfo: LoanOrderInfoEntity): Result {
        try {
            loanOrderInfo.id = CommonUtils.uuid
            loanOrderInfo.addTime = DateUtils.formatDateTime(LocalDateTime.now())
            loanOrderInfoService.save(loanOrderInfo)
        } catch (e: Exception) {
            log.error("保存贷款订单信息异常", e)
            return Result().error(msg = "保存贷款订单信息异常")
        }

        return Result().ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("loanorderinfo:update")
    fun update(@RequestBody loanOrderInfo: LoanOrderInfoEntity): Result {
        try {
            loanOrderInfo.updateTime = DateUtils.formatDateTime(LocalDateTime.now())
            loanOrderInfoService.update(loanOrderInfo)
        } catch (e: Exception) {
            log.error("修改贷款订单信息异常", e)
            return Result().error(msg = "修改贷款订单信息异常")
        }

        return Result().ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("loanorderinfo:delete")
    fun delete(@RequestBody ids: Array<String>): Result {
        try {
            when {
                ids.size == 1 -> {
                    val loanOrderInfoEntity = loanOrderInfoService.queryObject(ids[0])
                    loanOrderInfoEntity.isDelete = 0
                    loanOrderInfoService.update(loanOrderInfoEntity)
                }
                else -> ids.forEach {
                    val loanOrderInfoEntity = loanOrderInfoService.queryObject(it)
                    loanOrderInfoEntity.isDelete = 0
                    loanOrderInfoService.update(loanOrderInfoEntity)
                }
            }
        } catch (e: Exception) {
            log.error("删除贷款订单信息异常", e)
            return Result().error(msg = "删除贷款订单信息异常")
        }

        return Result().ok()
    }

}
