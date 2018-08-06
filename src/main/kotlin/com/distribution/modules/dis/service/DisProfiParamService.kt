package com.distribution.modules.dis.service

import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.entity.DisProfiParam


/**
 * 分润参数设置
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-22
 */
interface DisProfiParamService {
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    fun queryObject(id: String): DisProfiParam

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<DisProfiParam>

    /**
     * 保存
     *
     * @param disProfiParam
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(disProfiParam: DisProfiParam)

    /**
     * 更新
     *
     * @param disProfiParam
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(disProfiParam: DisProfiParam)

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Throws(Exception::class)
    fun delete(id: String)

    /**
     * 批量删除
     *
     * @param ids
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deleteBatch(ids: Array<String>)

    /**
     * 分润逻辑
     *
     * @param member   会员信息
     * @param money    金额
     * @param isReward 是否是会员推荐奖励
     */
    @Throws(Exception::class)
    fun doFeeSplitting(member: DisMemberInfoEntity, money: Double, isReward: Boolean)
}
