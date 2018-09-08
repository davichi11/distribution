package com.distribution.modules.dis.service

import com.distribution.modules.dis.entity.FeedbackEntity


/**
 * 意见反馈表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-08-18
 */
interface FeedbackService {
    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: Long): FeedbackEntity?

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<FeedbackEntity>?

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(feedback: FeedbackEntity)

    /**
     * 更新
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(feedback: FeedbackEntity)

    /**
     * 删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: Long)

    /**
     * 批量删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(ids: Array<Long>)
}
