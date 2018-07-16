package com.distribution.modules.integral.service.impl

import com.alibaba.fastjson.JSON
import com.distribution.modules.dis.dao.DisMemberInfoDao
import com.distribution.modules.dis.service.DisProfiParamService
import com.distribution.modules.integral.dao.IntegralOrderDao
import com.distribution.modules.integral.entity.IntegralOrderEntity
import com.distribution.modules.integral.service.IntegralOrderService
import com.distribution.queue.LevelUpSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("integralOrderService")
class IntegralOrderServiceImpl : IntegralOrderService {
    @Autowired
    private lateinit var integralOrderDao: IntegralOrderDao
    @Autowired
    private lateinit var disProfiParamService: DisProfiParamService
    @Autowired
    private lateinit var disMemberInfoDao: DisMemberInfoDao
    @Autowired
    private lateinit var levelUpSender: LevelUpSender

    override fun queryObject(id: String): IntegralOrderEntity {
        return integralOrderDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<IntegralOrderEntity> {
        return integralOrderDao.queryList(map)
    }


    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(integralOrder: IntegralOrderEntity) {
        integralOrderDao.save(integralOrder)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(integralOrder: IntegralOrderEntity) {
        integralOrderDao.update(integralOrder)
        //申请成功,执行分润
        if (integralOrder.status == 1) {
            val member = disMemberInfoDao.queryByMobile(integralOrder.mobile.toString())
            //如果当前办卡人和其上级都是非会员,则跳过分润
            if ("0" == member.disUserType && "0" == member.disMemberParent!!.disUserType) {
                return
            }
            //调用分润
            disProfiParamService.doFeeSplitting(member, integralOrder.profiMoney, false)
            if ("0" == member.disMemberParent!!.disUserType) {
                //执行会员升级
                levelUpSender.send(JSON.toJSONString(member.disMemberParent))
            }
        }
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: String) {
        integralOrderDao.delete(id)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        integralOrderDao.deleteBatch(ids)
    }

}
