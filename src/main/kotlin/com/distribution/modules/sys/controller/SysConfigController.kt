package com.distribution.modules.sys.controller

import com.distribution.common.annotation.SysLog
import com.distribution.common.utils.Result
import com.distribution.common.validator.ValidatorUtils
import com.distribution.modules.sys.entity.SysConfigEntity
import com.distribution.modules.sys.service.SysConfigService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 系统配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
class SysConfigController : AbstractController() {
    @Autowired
    private lateinit var sysConfigService: SysConfigService

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<SysConfigEntity> { sysConfigService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 配置信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    fun info(@PathVariable("id") id: Long): Result {
        val config = sysConfigService.queryObject(id)

        return Result().ok().put("config", config)
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    fun save(@RequestBody config: SysConfigEntity): Result {
        ValidatorUtils.validateEntity(config)

        try {
            sysConfigService.save(config)
        } catch (e: Exception) {
            logger.error("保存配置异常", e)
            return Result().error(msg = "保存配置异常")
        }

        return Result().ok()
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    fun update(@RequestBody config: SysConfigEntity): Result {
        ValidatorUtils.validateEntity(config)

        try {
            sysConfigService.update(config)
        } catch (e: Exception) {
            logger.error("修改配置异常", e)
            return Result().error(msg = "修改配置异常")
        }

        return Result().ok()
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    fun delete(@RequestBody ids: Array<Long>): Result {
        try {
            sysConfigService.deleteBatch(ids)
        } catch (e: Exception) {
            logger.error("删除配置异常", e)
            return Result().error(msg = "删除配置异常")
        }

        return Result().ok()
    }

}
