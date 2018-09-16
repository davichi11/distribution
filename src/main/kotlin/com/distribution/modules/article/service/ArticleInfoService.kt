package com.distribution.modules.article.service

import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.CacheUpdate
import com.alicp.jetcache.anno.Cached
import com.distribution.modules.article.entity.ArticleInfoEntity
import java.util.concurrent.TimeUnit


/**
 * 文章表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-29
 */
interface ArticleInfoService {
    /**
     * 根据ID查询
     * @return
     */
    fun queryObject(id: Int): ArticleInfoEntity

    /**
     * 查询列表
     * @param map
     * @return
     */
    @Cached(name = "ArticleInfoService.list",localExpire = 60000, cacheType = CacheType.BOTH, expire = 60000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryList(map: Map<String, Any>): List<ArticleInfoEntity>

    /**
     * 保存
     * @throws Exception
     */
    @CacheUpdate(name = "ArticleInfoService.list",key = "#articleInfo.id",value = "#articleInfo")
    @Throws(Exception::class)
    fun save(articleInfo: ArticleInfoEntity)

    /**
     * 更新
     * @throws Exception
     */
    @CacheUpdate(name = "ArticleInfoService.list",key = "#articleInfo.id",value = "#articleInfo")
    @Throws(Exception::class)
    fun update(articleInfo: ArticleInfoEntity)

    /**
     * 删除
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: Int)

    /**
     * 批量删除
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(ids: Array<Int>)
}
