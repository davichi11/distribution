package com.distribution.modules.pos.service.impl

import com.distribution.modules.pos.dao.PosApplyDao
import com.distribution.modules.pos.entity.PosApplyEntity
import com.distribution.modules.pos.service.PosApplyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("posApplyService")
class PosApplyServiceImpl : PosApplyService {
    @Autowired
    private lateinit var posApplyDao: PosApplyDao

    override fun queryObject(id: String): PosApplyEntity {
        return posApplyDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<PosApplyEntity> {
        return posApplyDao.queryList(map)
    }


    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(PosApply: PosApplyEntity) {
        posApplyDao.save(PosApply)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(PosApply: PosApplyEntity) {
        posApplyDao.update(PosApply)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: String) {
        posApplyDao.delete(id)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        posApplyDao.deleteBatch(ids)
    }

}
