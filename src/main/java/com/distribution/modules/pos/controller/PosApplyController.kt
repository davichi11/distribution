package com.distribution.modules.pos.controller

import com.distribution.common.utils.Result
import com.distribution.modules.pos.entity.PosApplyEntity
import com.distribution.modules.pos.service.PosApplyService
import com.github.pagehelper.PageHelper
import lombok.extern.slf4j.Slf4j
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
 * POS机申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-06-20
 */

@RestController
@RequestMapping("posapply")
class PosApplyController {
    private val log = LoggerFactory.getLogger(PosApplyController::class.java)

    @Autowired
    private lateinit var posApplyService: PosApplyService

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("posapply:list")
    fun list(@RequestParam params: Map<String, Any>): Result? {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo<PosApplyEntity> { posApplyService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("posapply:info")
    fun info(@PathVariable("id") id: String): Result? {
        val posApply = posApplyService.queryObject(id)

        return Result().ok().put("posApply", posApply)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("posapply:save")
    fun save(@RequestBody posApply: PosApplyEntity): Result {
        try {
            posApplyService.save(posApply)
        } catch (e: Exception) {
            log.error("保存POS机申请异常", e)
            return Result().error(msg = "保存POS机申请异常")
        }

        return Result().ok()
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("posapply:update")
    fun update(@RequestBody posApply: PosApplyEntity): Result {
        try {
            posApplyService.update(posApply)
        } catch (e: Exception) {
            log.error("修改POS机申请异常", e)
            return Result().error(msg = "修改POS机申请异常")
        }

        return Result().ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("posapply:delete")
    fun delete(@RequestBody ids: Array<String>): Result {
        try {
            if (ids.size == 1) {
                posApplyService.delete(ids[0])
            } else {
                posApplyService.deleteBatch(ids)
            }
        } catch (e: Exception) {
            log.error("删除POS机申请异常", e)
            return Result().error(msg = "删除POS机申请异常")
        }

        return Result().ok()
    }

}
