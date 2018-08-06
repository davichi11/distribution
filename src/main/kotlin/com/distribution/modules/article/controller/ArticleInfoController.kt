package com.distribution.modules.article.controller

import com.distribution.common.utils.Result
import com.distribution.modules.article.entity.ArticleInfoEntity
import com.distribution.modules.article.service.ArticleInfoService
import com.github.pagehelper.PageHelper
import lombok.extern.slf4j.Slf4j
import org.apache.commons.collections.MapUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
 * 文章表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-29
 */
@Slf4j
@RestController
@RequestMapping("articleinfo")
class ArticleInfoController {
    @Autowired
    private lateinit var articleInfoService: ArticleInfoService
    private val log = LoggerFactory.getLogger(ArticleInfoController::class.java)
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("articleinfo:list")
    fun list(@RequestParam params: Map<String, Any>): Result {
        //查询列表数据
        val pageInfo = PageHelper.startPage<Any>(MapUtils.getInteger(params, "page", 1),
                MapUtils.getInteger(params, "limit", 10)).doSelectPageInfo<ArticleInfoEntity> { articleInfoService.queryList(params) }
        return Result().ok().put("page", pageInfo)
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("articleinfo:info")
    fun info(@PathVariable("id") id: Int): Result {
        val articleInfo = articleInfoService.queryObject(id)

        return Result().ok().put("articleInfo", articleInfo)
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("articleinfo:save")
    fun save(@RequestBody articleInfo: ArticleInfoEntity): Result {
        try {
            articleInfoService.save(articleInfo)
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
    @RequiresPermissions("articleinfo:update")
    fun update(@RequestBody articleInfo: ArticleInfoEntity): Result {
        try {
            articleInfoService.update(articleInfo)
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
    @RequiresPermissions("articleinfo:delete")
    fun delete(@RequestBody ids: Array<Int>): Result {
        try {
            when {
                ids.size == 1 -> {
                    val articleInfo = articleInfoService.queryObject(ids[0])
                    articleInfo.isDelete = "0"
                    articleInfoService.update(articleInfo)
                }
                else -> ids.forEach {
                    val articleInfo = articleInfoService.queryObject(it)
                    articleInfo.isDelete = "0"
                    articleInfoService.update(articleInfo)
                }
            }
        } catch (e: Exception) {
            log.error("删除异常", e)
            return Result().error(msg = "删除异常")
        }

        return Result().ok()
    }

}
