package com.distribution.modules.dis.service

import com.distribution.modules.dis.entity.CardOrderInfoEntity


/**
 * 办卡订单信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */
interface CardOrderInfoService {
    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: String): CardOrderInfoEntity

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<CardOrderInfoEntity>

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(cardOrderInfo: CardOrderInfoEntity)

    /**
     * 更新
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(cardOrderInfo: CardOrderInfoEntity)

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

    /**
     * 根据查询条件统计订单数量
     *
     * @param param
     * @return
     */
    fun countOrder(param: Map<String, Any>): Int?

    /**
     * 统计用户办理的某张卡片的数量
     * @param memberId
     * @param banNum
     * @return
     */
    fun countUserCard(memberId: String, banNum: String): Int?

    /**
     * 批量修改订单状态
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/27 15:22
     * @Description:
     */
    @Throws(Exception::class)
    fun statusUpdate(map: Map<String, Any>)
}
