package com.distribution.modules.account.controller

import com.distribution.common.utils.Result
import com.distribution.modules.account.entity.MemberAccount
import com.distribution.modules.account.service.MemberAccountService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


/**
 * 会员账户表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-08-26
 */
@RestController
@RequestMapping("memberaccount")
class MemberAccountController {
    @Autowired
    private lateinit var memberAccountService: MemberAccountService
    private val log = LoggerFactory.getLogger(MemberAccountController::class.java)
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("memberaccount:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        val filterValues = params.filterValues { (it as String).isNotEmpty() }
        //查询列表数据
        val pageInfo = PageHelper.startPage<MemberAccount>(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit"))
                .doSelectPageInfo<MemberAccount> { memberAccountService.queryList(filterValues) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{accountId}")
    @RequiresPermissions("memberaccount:info")
    fun info(@PathVariable("accountId") accountId: String): Result {
        val memberAccount = memberAccountService.queryObject(accountId)

        return Result().ok().put("memberAccount", memberAccount ?: "")
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("memberaccount:save")
    fun save(memberAccount: MemberAccount): Result {
        try {
            memberAccountService.save(memberAccount)
        } catch (e: Exception) {
            log.error("保存异常", e)
            return Result().error(msg = "保存异常")
        }

        return Result().ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("memberaccount:update")
    fun update(memberAccount: MemberAccount): Result {
        try {
            memberAccountService.update(memberAccount)
        } catch (e: Exception) {
            log.error("修改异常", e)
            return Result().error(msg = "修改异常")
        }

        return Result().ok()
    }


}
