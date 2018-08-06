package com.distribution.modules.dis.controller

import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.dis.entity.LoanInfoEntity
import com.distribution.modules.dis.service.LoanInfoService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


/**
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-06
 */

@RestController
@RequestMapping("loaninfo")
class LoanInfoController {
    @Autowired
    private lateinit var loanInfoService: LoanInfoService
    private val log = LoggerFactory.getLogger(LoanInfoController::class.java)

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("loaninfo:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo<LoanInfoEntity> { loanInfoService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("loaninfo:info")
    fun info(@PathVariable("id") id: String): Result {
        val loanInfo = loanInfoService.queryObject(id)

        return Result().ok().put("loanInfo", loanInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("loaninfo:save")
    fun save(@RequestBody loanInfo: LoanInfoEntity): Result {
        try {
            loanInfo.addTime = DateUtils.formatDateTime(LocalDateTime.now())
            loanInfoService.save(loanInfo)
        } catch (e: Exception) {
            log.error("保存贷款产品信息异常", e)
            return Result().error(msg = "保存贷款产品信息异常")
        }

        return Result().ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("loaninfo:update")
    fun update(@RequestBody loanInfo: LoanInfoEntity): Result {
        try {
            if (loanInfo.id.isNotEmpty()) {
                loanInfo.updateTime = DateUtils.formatDateTime(LocalDateTime.now())
                loanInfoService.update(loanInfo)
            } else {
                loanInfo.addTime = DateUtils.formatDateTime(LocalDateTime.now())
                loanInfoService.save(loanInfo)
            }
        } catch (e: Exception) {
            log.error("修改贷款产品信息异常", e)
            return Result().error(msg = "修改贷款产品信息异常")
        }

        return Result().ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("loaninfo:delete")
    fun delete(@RequestBody ids: Array<String>): Result {
        try {
            when {
                ids.size == 1 -> {
                    val loanInfo = loanInfoService.queryObject(ids[0])
                    loanInfo.isDelete = 0
                    loanInfoService.update(loanInfo)
                }
                else -> ids.forEach {
                    val loanInfo = loanInfoService.queryObject(it)
                    loanInfo.isDelete = 0
                    loanInfoService.update(loanInfo)
                }
            }
        } catch (e: Exception) {
            log.error("删除贷款产品信息异常", e)
            return Result().error(msg = "删除贷款产品信息异常")
        }

        return Result().ok()
    }

}
