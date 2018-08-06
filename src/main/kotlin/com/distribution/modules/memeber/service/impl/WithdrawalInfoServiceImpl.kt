package com.distribution.modules.memeber.service.impl

import com.distribution.modules.memeber.dao.WithdrawalInfoDao
import com.distribution.modules.memeber.entity.WithdrawalInfo
import com.distribution.modules.memeber.service.WithdrawalInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class WithdrawalInfoServiceImpl : WithdrawalInfoService {

    @Autowired
    private lateinit var withdrawalInfoDao: WithdrawalInfoDao

    /**
     * 根据用户手机号查询用户提现金额
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/22 22:30
     * @Description:
     */
    override fun withdrawalAmounts(withdrawMobile: String): BigDecimal {
        val map = HashMap<String, Any>(2)
        map["withdrawMobile"] = withdrawMobile
        val withdrawalInfoList = withdrawalInfoDao.queryList(map)
        val aDouble = withdrawalInfoList.map { value -> value.withdrawAmount!!.toDouble() }.sum()
        return BigDecimal.valueOf(aDouble)
    }

    /**
     * 提现记录查询
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/22 22:32
     * @Description:
     */
    override fun queryList(params: Map<String, Any>): List<WithdrawalInfo> {
        return withdrawalInfoDao.queryList(params)
    }

    /**
     * 添加提现信息
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/22 22:50
     * @Description:
     */
    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(withdrawalInfo: WithdrawalInfo) {
        withdrawalInfoDao.save(withdrawalInfo)
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    override fun querySingle(id: String): WithdrawalInfo {
        return withdrawalInfoDao.queryObject(id)
    }
}
