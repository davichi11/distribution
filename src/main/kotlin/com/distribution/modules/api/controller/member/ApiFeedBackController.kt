package com.distribution.modules.api.controller.member

import com.distribution.common.utils.Result
import com.distribution.modules.api.pojo.vo.FeedBackVO
import com.distribution.modules.dis.entity.FeedbackEntity
import com.distribution.modules.dis.service.FeedbackService
import com.github.pagehelper.PageHelper
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.apache.commons.lang3.math.NumberUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller.member
 * @author ChunLiang Hu
 * @create 2018/8/18-23:09
 */
@Api("意见反馈相关接口")
@RestController
@RequestMapping("/api")
@CrossOrigin
class ApiFeedBackController {
    @Autowired
    private lateinit var feedbackService: FeedbackService
    private val log = LoggerFactory.getLogger(ApiFeedBackController::class.java)

    @GetMapping("/feedback")
    @ApiOperation(value = "根据手机号查询用户的反馈记录")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "mobile", value = "会员手机号", required = true)
    fun getFeedbackList(mobile: String, page: String = "0", limit: String = "0"): Result {
        val pageInfo = PageHelper.startPage<Any>(NumberUtils.toInt(page, 0), NumberUtils.toInt(limit, 0))
                .doSelectPageInfo<FeedbackEntity> { feedbackService.queryList(mapOf("mobile" to mobile)) }
        return Result().ok().put("page", pageInfo)
    }

    @ApiOperation(value = "根据id查询反馈记录")
    @ApiImplicitParam(paramType = "path", dataType = "long", name = "id",  required = true)
    @GetMapping("/feedback/{id}")
    fun getOneFeedbackById(@PathVariable id: Long): Result {
        val feedbackEntity = feedbackService.queryObject(id) ?: return Result().error(msg = "无此记录")
        return Result().ok().put("feedback", feedbackEntity)
    }

    @ApiOperation("添加意见反馈")
    @ApiImplicitParam(paramType = "body", dataType = "FeedBackVO", name = "feedbackVO",  required = true)
    @PostMapping("/feedback")
    fun addFeedBack(feedbackVO: FeedBackVO): Result {
        if (feedbackVO.mobile == null) {
            return Result().error(msg = "用户不能为空")
        }
        val feedback = FeedbackEntity()
        BeanUtils.copyProperties(feedbackVO, feedback)
        return try {
            feedbackService.save(feedback)
            Result().ok()
        } catch (e: Exception) {
            log.error("提交意见反馈异常", e)
            Result().error(msg = "提交意见反馈异常")
        }
    }
}