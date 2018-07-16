package com.distribution.modules.dis.controller

import com.distribution.common.utils.Result
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.service.DisMemberInfoService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


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
    private val log = LoggerFactory.getLogger(DisMemberInfoController::class.java)

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("dismemberinfo:list")
    fun list(@RequestParam params: Map<String, Any>): Result? {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<DisMemberInfoEntity> { disMemberInfoService.queryList(params) }

        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("dismemberinfo:info")
    fun info(@PathVariable("id") id: String): Result? {
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

}
