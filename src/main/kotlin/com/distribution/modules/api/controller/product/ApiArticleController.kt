package com.distribution.modules.api.controller.product

import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.Result
import com.distribution.modules.api.annotation.AuthIgnore
import com.distribution.modules.article.service.AdPictureService
import com.distribution.modules.article.service.ArticleInfoService
import com.distribution.modules.card.entity.CardInfo
import com.github.pagehelper.PageHelper
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.controller
 * @author ChunLiang Hu
 * @create 2018/7/29-13:00
 */
@Api("文章,轮播图接口")
@RestController
@RequestMapping("/api/article")
class ApiArticleController {
    @Autowired
    private lateinit var adPictureService: AdPictureService
    @Autowired
    private lateinit var articleInfoService: ArticleInfoService

    @AuthIgnore
    @ApiOperation("查询轮播图")
    @GetMapping("/adPicture")
    fun getPictures(): Result {
        val pics = adPictureService.queryList(mapOf())
        return Result().ok().put("pics", pics)
    }

    @AuthIgnore
    @ApiOperation("查询新闻")
    @GetMapping("/articles")
    fun getArticles(page: String = "0", limit: String = "0", type: String = "0"): Result {
        val pageInfo = PageHelper.startPage<Any>(NumberUtils.toInt(page, 0), NumberUtils.toInt(limit, 0))
                .doSelectPageInfo<CardInfo> { articleInfoService.queryList(mapOf("type" to type)) }
        return Result().ok().put("page", pageInfo)
    }

    @AuthIgnore
    @ApiOperation("根据id和类型查询新闻")
    @GetMapping("/article/{id}/{type}")
    fun getArticleByIdAndType(@PathVariable id: Int, @PathVariable type: String): Result {
        val articleInfo = articleInfoService.queryList(mapOf("id" to id, "type" to type))[0]
        //Markdown转为HTML
        articleInfo.detail = CommonUtils.mdToHtml(articleInfo.detail)
        return Result().ok().put("article", articleInfo)

    }

}