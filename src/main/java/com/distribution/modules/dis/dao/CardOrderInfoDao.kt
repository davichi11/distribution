package com.distribution.modules.dis.dao

import com.distribution.modules.dis.entity.CardOrderInfoEntity
import com.distribution.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * 办卡订单信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */
@Mapper
interface CardOrderInfoDao : BaseDao<CardOrderInfoEntity> {
    /**
     * 根据查询条件查询订单数量
     *
     * @param param
     * @return
     */
    fun countOrder(param: Map<String, Any>): Int?

    /**
     * 统计用户本月内办理某张银行信用卡数量
     * @param memberId
     * @param bankNum
     * @param startDate
     * @param endDate
     * @return
     */
    fun countUserCard(@Param("memberId") memberId: String, @Param("bankNum") bankNum: String,
                      @Param("startDate") startDate: String, @Param("endDate") endDate: String): Int?

    /**
     * 批量修改订单状态
     *
     * @param map
     */
    fun statusUpdate(map: Map<String, Any>)

    /**
     * 批量查询办卡订单
     * @param ids
     * @return
     */
    fun queryListByIds(ids: List<*>): List<CardOrderInfoEntity>


}
