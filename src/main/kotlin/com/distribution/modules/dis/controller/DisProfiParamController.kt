package com.distribution.modules.dis.controller

import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.dis.entity.DisProfiParam
import com.distribution.modules.dis.service.DisProfiParamService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


/**
 * 分润参数设置
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-22
 */

@RestController
@RequestMapping("disprofiparam")
class DisProfiParamController {
    @Autowired
    private lateinit var disProfiParamService: DisProfiParamService
    private val log = LoggerFactory.getLogger(DisProfiParamController::class.java)

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("disprofiparam:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<DisProfiParam> { disProfiParamService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("disprofiparam:info")
    fun info(@PathVariable("id") id: String): Result {
        val disProfiParam = disProfiParamService.queryObject(id)

        return Result().ok().put("disProfiParam", disProfiParam)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("disprofiparam:save")
    fun save(@RequestBody disProfiParam: DisProfiParam): Result {
        disProfiParam.id = CommonUtils.uuid
        disProfiParam.addTime = DateUtils.formatDateTime(LocalDateTime.now())
        try {
            disProfiParamService.save(disProfiParam)
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
    @RequiresPermissions("disprofiparam:update")
    fun update(@RequestBody disProfiParam: DisProfiParam): Result {
        disProfiParam.updateTime = DateUtils.formatDateTime(LocalDateTime.now())
        try {
            disProfiParamService.update(disProfiParam)
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
    @RequiresPermissions("disprofiparam:delete")
    fun delete(@RequestBody ids: Array<String>): Result {
        try {
            if (ids.size == 1) {
                disProfiParamService.delete(ids[0])
            } else {
                disProfiParamService.deleteBatch(ids)
            }
        } catch (e: Exception) {
            log.error("删除分润参数异常", e)
            Result().error(msg = "删除分润参数异常")
        }

        return Result().ok()
    }

}
