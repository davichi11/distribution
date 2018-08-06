package com.distribution.modules.card.service

import com.distribution.modules.card.entity.CardApiResponse
import com.distribution.modules.card.entity.CardInfo
import com.distribution.modules.dis.entity.DisMemberInfoEntity

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.card.service
 * @Description TODO(描述)
 * @create 2018/5/8-20:21
 */
interface CardInfoService {

    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: String): CardInfo

    fun queryByBankNum(bankNum: String): CardInfo

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<CardInfo>

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(cardInfo: CardInfo)

    /**
     * 更新
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(cardInfo: CardInfo)

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
     * 获取信用卡信息
     * @param prodId
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getProductInfo(prodId: String): CardApiResponse

    /**
     * 提交用户信息并返回办卡链接,失败返回空
     * @param member
     * @param prodId
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getProductUrl(member: DisMemberInfoEntity, prodId: String): String
}