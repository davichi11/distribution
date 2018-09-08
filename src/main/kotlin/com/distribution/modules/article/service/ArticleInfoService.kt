package com.distribution.modules.article.service

import com.distribution.modules.article.entity.ArticleInfoEntity


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
    fun queryList(map: Map<String, Any>): List<ArticleInfoEntity>

    /**
     * 保存
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(articleInfo: ArticleInfoEntity)

    /**
     * 更新
     * @throws Exception
     */
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
