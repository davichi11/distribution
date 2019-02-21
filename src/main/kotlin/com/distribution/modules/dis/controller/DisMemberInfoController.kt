package com.distribution.modules.dis.controller

import com.distribution.common.excel.ExcelUtils
import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.account.entity.MemberAccountHistory
import com.distribution.modules.account.service.MemberAccountHistoryService
import com.distribution.modules.account.service.MemberAccountService
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.service.DisMemberInfoService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.commons.lang3.StringUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal
import java.time.LocalDateTime


/**
 * 用户表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-03
 */
@RestController
@RequestMapping("dismemberinfo")
class DisMemberInfoController {
    @Autowired
    private lateinit var disMemberInfoService: DisMemberInfoService
    @Autowired
    private lateinit var memberAccountService: MemberAccountService
    @Autowired
    private lateinit var historyService: MemberAccountHistoryService

    private val log = LoggerFactory.getLogger(DisMemberInfoController::class.java)

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("dismemberinfo:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<DisMemberInfoEntity> { disMemberInfoService.queryByPage(params) }

        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("dismemberinfo:info")
    fun info(@PathVariable("id") id: String): Result {
        val disMemberInfo = disMemberInfoService.queryObject(id)
        return if (disMemberInfo == null) Result().error(msg = "无数据")
        else Result().ok().put("disMemberInfo", disMemberInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("dismemberinfo:save")
    fun save(@RequestBody disMemberInfo: DisMemberInfoEntity): Result {
        try {
            disMemberInfoService.save(disMemberInfo)
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
    @RequiresPermissions("dismemberinfo:update")
    fun update(@RequestBody disMemberInfo: DisMemberInfoEntity): Result {
        try {
            disMemberInfoService.update(disMemberInfo)
        } catch (e: Exception) {
            log.error("修改异常", e)
            Result().error(msg = "修改异常")
        }

        return Result().ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("dismemberinfo:delete")
    fun delete(@RequestBody ids: Array<String>): Result {
        try {
            if (ids.size == 1) {
                disMemberInfoService.delete(ids[0])
            } else {
                disMemberInfoService.deleteBatch(ids)
            }
        } catch (e: Exception) {
            log.error("删除异常", e)
            Result().error(msg = "删除异常")
        }

        return Result().ok()
    }

    @RequestMapping("/batchSendMoney")
    fun batchSendMoney(file: MultipartFile?): Result {
        if (file == null) {
            return Result().error(msg = "文件未上传")
        }
        val errorList = mutableListOf<String>()
        ExcelUtils.readExcel(file.inputStream, 0).forEach { names ->
            names.filter { StringUtils.isNoneBlank(it) }.forEach {
                val memberAccount = memberAccountService.selectByUserName(it ?: "")
                if (memberAccount == null) {
                    errorList.add(it ?: "")
                } else {
                    memberAccount.forEach { account ->
                        account.memberAmount = account.memberAmount.inc()
                        memberAccountService.update(account)
                        //保存历史记录
                        val historyRecord = MemberAccountHistory()
                        historyRecord.id = CommonUtils.uuid
                        historyRecord.accountId = account.accountId
                        historyRecord.hisAmount = BigDecimal.ONE
                        historyRecord.hisType = MemberAccountHistory.HisType.INCOME
                        historyRecord.addTime = DateUtils.formatDateTime(LocalDateTime.now())
                        historyRecord.isDelete = "1"
                        historyService.saveHistory(historyRecord)
                    }
//                    memberAccountService.updateByUserName(it ?: "")
                }
            }
        }
        return Result().ok("操作成功").put("error", errorList)
    }

}
