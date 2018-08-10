package com.distribution.modules.dis.dao

import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.sys.dao.BaseDao
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

/**
 * 用户表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-03
 */
@Mapper
interface DisMemberInfoDao : BaseDao<DisMemberInfoEntity> {
    /**
     * 根据open的ID查询
     * @param openId
     * @return
     */
    fun findByOpenId(openId: String): DisMemberInfoEntity

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    fun queryForPage(map: Map<String, Any>): List<DisMemberInfoEntity>?

    fun queryByMobile(mobile: String): DisMemberInfoEntity

    fun queryByWorkerId(@Param("workerId") workerId: Long): DisMemberInfoEntity?

    @Throws(Exception::class)
    fun updateDisLevel(@Param("level") level: Int?, @Param("type") type: String, @Param("id") id: String)
}
