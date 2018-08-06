package com.distribution.modules.pos.service

import com.distribution.modules.pos.entity.PosApplyEntity


/**
 * POS机申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-06-20
 */
interface PosApplyService {
    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: String): PosApplyEntity

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<PosApplyEntity>

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(PosApply: PosApplyEntity)

    /**
     * 更新
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(PosApply: PosApplyEntity)

    /**
     * 删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: String)

    /**
     * 批量删除
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(ids: Array<String>)
}
