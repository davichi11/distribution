package com.distribution.modules.article.service

import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.CacheUpdate
import com.alicp.jetcache.anno.Cached
import com.distribution.modules.article.entity.AdPictureEntity
import java.util.concurrent.TimeUnit


/**
 * 轮播图
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-29
 */
interface AdPictureService {
    /**
     * 根据ID查询
     * @return
     */
    @Cached(name = "AdPictureService.", key = "#id", localExpire = 60000, cacheType = CacheType.BOTH, expire = 6000000, timeUnit = TimeUnit.MILLISECONDS)
    fun queryObject(id: Int): AdPictureEntity

    /**
     * 查询列表
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<AdPictureEntity>

    /**
     * 保存
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(adPicture: AdPictureEntity)

    /**
     * 更新
     * @throws Exception
     */
    @CacheUpdate(name = "AdPictureService.list", key = "#adPicture.id", value = "#adPicture")
    @Throws(Exception::class)
    fun update(adPicture: AdPictureEntity)

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
