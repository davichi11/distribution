package com.distribution.modules.article.service

import com.distribution.modules.article.entity.AdPictureEntity


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
