package com.distribution.modules.oss.service.impl

import com.distribution.modules.oss.dao.SysOssDao
import com.distribution.modules.oss.entity.SysOssEntity
import com.distribution.modules.oss.service.SysOssService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author huchunliang
 */
@Service("sysOssService")
class SysOssServiceImpl : SysOssService {
    @Autowired
    private lateinit var sysOssDao: SysOssDao

    override fun queryObject(id: Long): SysOssEntity {
        return sysOssDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<SysOssEntity> {
        return sysOssDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return sysOssDao.queryTotal(map)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(sysOss: SysOssEntity) {
        sysOssDao.save(sysOss)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(sysOss: SysOssEntity) {
        sysOssDao.update(sysOss)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: Long) {
        sysOssDao.delete(id)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<Long>) {
        sysOssDao.deleteBatch(ids)
    }

}
