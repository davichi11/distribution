package com.distribution.modules.card.controller

import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.Result
import com.distribution.modules.card.entity.CardInfo
import com.distribution.modules.card.service.CardInfoService
import com.github.pagehelper.PageHelper
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


/**
 * 可办信用卡信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */

@RestController
@RequestMapping("cardinfo")
class CardInfoController {
    @Autowired
    private lateinit var cardInfoService: CardInfoService
    private val log = LoggerFactory.getLogger(CardInfoController::class.java)

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cardinfo:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 0),
                MapUtils.getInteger(params, "limit", 0)).doSelectPageInfo<CardInfo> { cardInfoService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cardinfo:info")
    fun info(@PathVariable("id") id: String): Result {
        val cardInfo = cardInfoService.queryObject(id)

        return Result().ok().put("cardInfo", cardInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cardinfo:save")
    fun save(@RequestBody cardInfo: CardInfo): Result {
        try {
            cardInfo.id = CommonUtils.uuid
            cardInfo.addTime = DateUtils.formatDateTime(LocalDateTime.now())

            cardInfoService.save(cardInfo)
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
    @RequiresPermissions("cardinfo:update")
    fun update(@RequestBody cardInfo: CardInfo): Result {
        try {
            cardInfo.updateTime = DateUtils.formatDateTime(LocalDateTime.now())
            cardInfoService.update(cardInfo)
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
    @RequiresPermissions("cardinfo:delete")
    fun delete(@RequestBody ids: Array<String>): Result {
        try {
            if (ids.size == 1) {
                cardInfoService.delete(ids[0])
            } else {
                cardInfoService.deleteBatch(ids)
            }
        } catch (e: Exception) {
            log.error("删除信用卡信息异常", e)
            return Result().error(msg = "删除信用卡信息异常")
        }

        return Result().ok()
    }

}
