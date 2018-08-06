package com.distribution.modules.dis.service.impl

import com.distribution.modules.dis.dao.DisFansMapper
import com.distribution.modules.dis.entity.DisFans
import com.distribution.modules.dis.service.DisFansService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.dis.service.impl
 * @Description TODO(描述)
 * @create 2018/5/11-23:06
 */
@Service
class DisFansServiceImpl : DisFansService {
    @Autowired
    private lateinit var disFansMapper: DisFansMapper

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    override fun queryObject(id: String): DisFans {
        return disFansMapper.selectByPrimaryKey(id)
    }

    override fun queryByOpenId(openId: String): DisFans {
        return disFansMapper.findWechatId(openId)
    }

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    override fun queryList(map: Map<String, Any>): List<DisFans> {
        return disFansMapper.selectList(map)
    }

    /**
     * 保存
     *
     * @param disFans
     * @throws Exception
     */
    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(disFans: DisFans) {
        disFansMapper.insertSelective(disFans)
    }

    /**
     * 更新
     *
     * @param disFans
     * @throws Exception
     */
    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(disFans: DisFans) {
        disFansMapper.updateByPrimaryKeySelective(disFans)
    }

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: String) {
        disFansMapper.deleteByPrimaryKey(id)
    }

    /**
     * 批量删除
     *
     * @param ids
     * @throws Exception
     */
    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        disFansMapper.deleteBatch(ids)
    }
}
