package com.distribution.modules.sys.service.impl

import com.distribution.common.exception.RRException
import com.distribution.modules.sys.dao.SysConfigDao
import com.distribution.modules.sys.entity.SysConfigEntity
import com.distribution.modules.sys.service.SysConfigService
import com.google.gson.Gson
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("sysConfigService")
class SysConfigServiceImpl : SysConfigService {
    @Autowired
    private lateinit var sysConfigDao: SysConfigDao

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(config: SysConfigEntity) {
        sysConfigDao.save(config)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(config: SysConfigEntity) {
        sysConfigDao.update(config)
    }

    override fun updateValueByKey(key: String, value: String) {
        sysConfigDao.updateValueByKey(key, value)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<Long>) {
        sysConfigDao.deleteBatch(ids)
    }

    override fun queryList(map: Map<String, Any>): List<SysConfigEntity> {
        return sysConfigDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return sysConfigDao.queryTotal(map)
    }

    override fun queryObject(id: Long): SysConfigEntity {
        return sysConfigDao.queryObject(id)
    }

    override fun getValue(key: String, defaultValue: String?): String {
        val value = sysConfigDao.queryByKey(key)
        return if (StringUtils.isBlank(value)) {
            defaultValue!!
        } else value
    }

    override fun <T> getConfigObject(key: String, clazz: Class<T>): T {
        val value = getValue(key, null)
        if (StringUtils.isNotBlank(value)) {
            return Gson().fromJson(value, clazz)
        }

        try {
            return clazz.newInstance()
        } catch (e: Exception) {
            throw RRException("获取参数失败")
        }

    }
}
