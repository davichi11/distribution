package com.distribution.modules.dis.service

import com.distribution.modules.dis.entity.DisFans

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.dis.service
 * @Description TODO(描述)
 * @create 2018/5/11-23:04
 */
interface DisFansService {
    /**
     * 根据ID查询
     *
     * @return
     */
    fun queryObject(id: String): DisFans?

    fun queryByOpenId(openId: String): DisFans?

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryList(map: Map<String, Any>): List<DisFans>?

    /**
     * 保存
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun save(disFans: DisFans)

    /**
     * 更新
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun update(disFans: DisFans)

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
