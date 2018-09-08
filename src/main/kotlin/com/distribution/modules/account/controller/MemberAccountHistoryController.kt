package com.distribution.modules.account.controller

import com.distribution.common.utils.Result
import com.distribution.modules.account.entity.MemberAccountHistory
import com.distribution.modules.account.service.MemberAccountHistoryService
import com.github.pagehelper.PageHelper
import lombok.extern.slf4j.Slf4j
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


/**
 * 用户金额变化表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-08-26
 */
@Slf4j
@RestController
@RequestMapping("memberaccounthistory")
class MemberAccountHistoryController {
    @Autowired
    private lateinit var memberAccountHistoryService: MemberAccountHistoryService
    private val log = LoggerFactory.getLogger(MemberAccountHistoryController::class.java)
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("memberaccounthistory:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit"))
                .doSelectPageInfo<MemberAccountHistory> { memberAccountHistoryService.queryList(params.filterValues { (it as String).isNotEmpty() }) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("memberaccounthistory:info")
    fun info(@PathVariable("id") id: String): Result {
        val memberAccountHistory = memberAccountHistoryService.queryObject(id)

        return Result().ok().put("memberAccountHistory", memberAccountHistory ?: "")
    }


}
