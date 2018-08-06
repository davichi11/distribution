package com.distribution.modules.sys.service.impl

import com.distribution.common.utils.HttpContextUtils
import com.distribution.common.utils.IPUtils
import com.distribution.common.utils.ShiroUtils
import com.distribution.modules.sys.dao.SysLogDao
import com.distribution.modules.sys.entity.SysLogEntity
import com.distribution.modules.sys.service.SysLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * @author huchunliang
 */
@Service("sysLogService")
class SysLogServiceImpl : SysLogService {
    @Autowired
    private lateinit var sysLogDao: SysLogDao

    override fun queryObject(id: Long): SysLogEntity {
        return sysLogDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<SysLogEntity> {
        return sysLogDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return sysLogDao.queryTotal(map)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    @Throws(Exception::class)
    override fun save(sysLog: SysLogEntity) {
        sysLogDao.save(sysLog)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    @Throws(Exception::class)
    override fun update(sysLog: SysLogEntity) {
        sysLogDao.update(sysLog)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    @Throws(Exception::class)
    override fun delete(id: Long) {
        sysLogDao.delete(id)
    }

    @Transactional(rollbackFor = arrayOf(Exception::class))
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<Long>) {
        sysLogDao.deleteBatch(ids)
    }

    @Throws(Exception::class)
    override fun deleteByParams(param: Map<String, Any>) {
        sysLogDao.deleteByParams(param)
    }

    /**
     * 保存异常的日志信息
     *
     * @param params
     * @throws Exception
     */
    override fun saveErrorLog(params: String) {
        val logEntity = SysLogEntity()
        logEntity.createDate = LocalDateTime.now()
        //获取request
        val request = HttpContextUtils.httpServletRequest
        //设置IP地址
        logEntity.ip = IPUtils.getIpAddr(request)
        logEntity.params = params
        logEntity.username = ShiroUtils.userEntity.username
        logEntity.operation = "报错日志异常"
        try {
            save(logEntity)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
