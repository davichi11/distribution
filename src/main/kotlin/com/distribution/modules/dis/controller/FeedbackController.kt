package com.distribution.modules.dis.controller

import com.distribution.common.utils.Result
import com.distribution.modules.dis.entity.FeedbackEntity
import com.distribution.modules.dis.service.FeedbackService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
 * 意见反馈表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-08-18
 */
@RestController
@RequestMapping("feedback")
class FeedbackController {
    @Autowired
    private lateinit var feedbackService: FeedbackService
    private val log = LoggerFactory.getLogger(FeedbackController::class.java)
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("feedback:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo<FeedbackEntity> { feedbackService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("feedback:info")
    fun info(@PathVariable("id") id: Long): Result {
        val feedback = feedbackService.queryObject(id) ?: return Result().error(msg = "没有此记录")
        return Result().ok().put("feedback", feedback)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("feedback:save")
    fun save(feedback: FeedbackEntity): Result {
        try {
            feedbackService.save(feedback)
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
    @RequiresPermissions("feedback:update")
    fun update(feedback: FeedbackEntity): Result {
        try {
            feedbackService.update(feedback)
        } catch (e: Exception) {
            log.error("修改异常", e)
            return Result().error(msg = "修改异常")
        }

        return Result().ok()
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("feedback:delete")
    fun delete(@RequestBody ids: Array<Long>): Result {
        try {
            if (ids.size == 1) {
                val feedbackEntity = feedbackService.queryObject(ids[0])
                if (feedbackEntity != null) {
                    feedbackEntity.isDelete = 0
                    feedbackService.update(feedbackEntity)
                }
            } else {
                ids.forEach {
                    val feedbackEntity = feedbackService.queryObject(it)
                    if (feedbackEntity != null) {
                        feedbackEntity.isDelete = 0
                        feedbackService.update(feedbackEntity)
                    }
                }
            }
        } catch (e: Exception) {
            log.error("删除异常", e)
            return Result().error(msg = "删除异常")
        }

        return Result().ok()
    }

}
