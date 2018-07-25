package com.distribution.modules.sys.controller

import com.distribution.common.utils.Result
import com.distribution.modules.sys.entity.SysLogEntity
import com.distribution.modules.sys.service.SysLogService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


/**
 * 系统日志
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Controller
@RequestMapping("/sys/log")
class SysLogController {
    @Autowired
    private lateinit var sysLogService: SysLogService

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0)!!,
                MapUtils.getInteger(params, "limit", 0)!!).doSelectPageInfo<SysLogEntity> { sysLogService!!.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }

}
