package com.distribution.modules.integral.controller

import com.distribution.common.utils.Result
import com.distribution.modules.integral.entity.IntegralOrderEntity
import com.distribution.modules.integral.service.IntegralOrderService
import com.github.pagehelper.PageHelper
import lombok.extern.slf4j.Slf4j
import org.apache.commons.collections.MapUtils
import org.apache.commons.lang3.math.NumberUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
 * 积分兑换申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */

@RestController
@RequestMapping("integralorder")
class IntegralOrderController {
    @Autowired
    private lateinit var integralOrderService: IntegralOrderService
    private val log = LoggerFactory.getLogger(IntegralOrderController::class.java)

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("integralorder:list")
    fun list(@RequestParam params: Map<String, Any>): Result? {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo<IntegralOrderEntity> { integralOrderService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("integralorder:info")
    fun info(@PathVariable("id") id: String): Result? {
        val integralOrder = integralOrderService.queryObject(id)

        return Result().ok().put("integralOrder", integralOrder)
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("integralorder:delete")
    fun delete(@RequestBody ids: Array<String>): Result {
        try {
            if (ids.size == 1) {
                integralOrderService.delete(ids[0])
            } else {
                integralOrderService.deleteBatch(ids)
            }
        } catch (e: Exception) {
            log.error("删除积分兑换订单异常", e)
            return Result().error(msg = "删除积分兑换订单异常")
        }

        return Result().ok()
    }

    /**
     * 审核
     *
     * @param id
     * @param status
     * @return
     */
    @PatchMapping("/{id}/{status}/{money}")
    fun approve(@PathVariable("id") id: String, @PathVariable("status") status: String,
                @PathVariable("money") money: Double): Result {
        val entity = integralOrderService.queryObject(id)
        entity.status = NumberUtils.toInt(status)
        entity.profiMoney = money
        return try {
            integralOrderService.update(entity)
            Result().ok("审核成功")
        } catch (e: Exception) {
            log.error("审核异常", e)
            Result().error(msg = "审核异常")
        }

    }

}
