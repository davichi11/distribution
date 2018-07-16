package com.distribution.modules.memeber.controller

import com.distribution.common.utils.Result
import com.distribution.modules.memeber.entity.WithdrawalInfo
import com.distribution.modules.memeber.service.WithdrawalInfoService
import com.github.pagehelper.PageHelper
import lombok.extern.slf4j.Slf4j
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
 * 提现信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-30
 */

@RestController
@RequestMapping("withdrawal")
class WithdrawalInfoController {
    @Autowired
    private lateinit var withdrawalInfoService: WithdrawalInfoService
    private val log = LoggerFactory.getLogger(WithdrawalInfoController::class.java)

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("withdrawalinfo:list")
    fun list(@RequestParam params: Map<String, Any>): Result? {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<WithdrawalInfo> { withdrawalInfoService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("withdrawalinfo:info")
    fun info(@PathVariable("id") id: String): Result? {
        val withdrawalInfo = withdrawalInfoService.querySingle(id)

        return Result().ok().put("withdrawalInfo", withdrawalInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("withdrawalinfo:save")
    fun save(@RequestBody withdrawalInfo: WithdrawalInfo): Result {
        try {
            withdrawalInfoService.save(withdrawalInfo)
        } catch (e: Exception) {
            log.error("保存异常", e)
            Result().error(msg = "保存异常")
        }

        return Result().ok()
    }


}
