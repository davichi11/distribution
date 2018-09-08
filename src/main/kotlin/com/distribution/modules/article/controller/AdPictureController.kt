package com.distribution.modules.article.controller

import com.distribution.common.utils.Result
import com.distribution.modules.article.entity.AdPictureEntity
import com.distribution.modules.article.service.AdPictureService
import com.github.pagehelper.PageHelper
import lombok.extern.slf4j.Slf4j
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
 * 轮播图
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-29
 */
@Slf4j
@RestController
@RequestMapping("picture")
class AdPictureController {
    @Autowired
    private lateinit var adPictureService: AdPictureService
    private val log = LoggerFactory.getLogger(AdPictureController::class.java)
    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("adpicture:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page"),
                MapUtils.getInteger(params, "limit")).doSelectPageInfo<AdPictureEntity> { adPictureService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("adpicture:info")
    fun info(@PathVariable("id") id: Int): Result {
        val adPicture = adPictureService.queryObject(id)

        return Result().ok().put("adPicture", adPicture)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("adpicture:save")
    fun save(@RequestBody adPicture: AdPictureEntity): Result {
        try {
            adPictureService.save(adPicture)
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
    @RequiresPermissions("adpicture:update")
    fun update(@RequestBody adPicture: AdPictureEntity): Result {
        try {
            adPictureService.update(adPicture)
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
    @RequiresPermissions("adpicture:delete")
    fun delete(@RequestBody ids: Array<Int>): Result {
        try {
            when {
                ids.size == 1 -> {
                    val pictureEntity = adPictureService.queryObject(ids[0])
                    pictureEntity.isDelete = "0"
                    adPictureService.update(pictureEntity)
                }
                else -> ids.forEach {
                    val pictureEntity = adPictureService.queryObject(it)
                    pictureEntity.isDelete = "0"
                    adPictureService.update(pictureEntity)
                }
            }
        } catch (e: Exception) {
            log.error("删除异常", e)
            return Result().error(msg = "删除异常")
        }

        return Result().ok()
    }

}
