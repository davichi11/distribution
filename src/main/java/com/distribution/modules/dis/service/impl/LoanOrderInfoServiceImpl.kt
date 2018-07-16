package com.distribution.modules.dis.service.impl

import com.alibaba.fastjson.JSON
import com.distribution.modules.dis.dao.DisMemberInfoDao
import com.distribution.modules.dis.dao.LoanOrderInfoDao
import com.distribution.modules.dis.entity.LoanOrderInfoEntity
import com.distribution.modules.dis.service.DisProfiParamService
import com.distribution.modules.dis.service.LoanOrderInfoService
import com.distribution.queue.LevelUpSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("loanOrderInfoService")
class LoanOrderInfoServiceImpl : LoanOrderInfoService {
    @Autowired
    private lateinit var loanOrderInfoDao: LoanOrderInfoDao
    @Autowired
    private lateinit var disMemberInfoDao: DisMemberInfoDao
    @Autowired
    private lateinit var disProfiParamService: DisProfiParamService
    @Autowired
    private lateinit var levelUpSender: LevelUpSender

    override fun queryObject(id: String): LoanOrderInfoEntity {
        return loanOrderInfoDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<LoanOrderInfoEntity> {
        return loanOrderInfoDao.queryList(map)
    }


    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(loanOrderInfo: LoanOrderInfoEntity) {
        loanOrderInfoDao.save(loanOrderInfo)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(loanOrderInfo: LoanOrderInfoEntity) {
        //这里要调取分润
        loanOrderInfoDao.update(loanOrderInfo)
        //申请成功,执行分润
        if (loanOrderInfo.orderStatus == 1) {
            val member = disMemberInfoDao.queryByMobile(loanOrderInfo.orderMobile!!)
            //如果当前办卡人和其上级都是非会员,则跳过分润
            if ("0" == member.disUserType && "0" == member.disMemberParent!!.disUserType) {
                return
            }
            //调用分润
            disProfiParamService.doFeeSplitting(member, loanOrderInfo.loanMoney!!, false)
            if ("0" == member.disMemberParent!!.disUserType) {
                //执行会员升级
                levelUpSender.send(JSON.toJSONString(member.disMemberParent))
            }
        }
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: String) {
        loanOrderInfoDao.delete(id)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        loanOrderInfoDao.deleteBatch(ids)
    }

}
