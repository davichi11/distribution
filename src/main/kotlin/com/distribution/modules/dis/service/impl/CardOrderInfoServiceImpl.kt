package com.distribution.modules.dis.service.impl

import com.alibaba.fastjson.JSON
import com.distribution.common.exception.RRException
import com.distribution.common.utils.DateUtils
import com.distribution.common.utils.DateUtils.endDateOfMonth
import com.distribution.common.utils.DateUtils.startDateOfMonth
import com.distribution.modules.dis.dao.CardOrderInfoDao
import com.distribution.modules.dis.dao.DisMemberInfoDao
import com.distribution.modules.dis.entity.CardOrderInfoEntity
import com.distribution.modules.dis.service.CardOrderInfoService
import com.distribution.modules.dis.service.DisProfiParamService
import com.distribution.queue.LevelUpSender
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.commons.collections.MapUtils
import org.apache.commons.lang.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service("cardOrderInfoService")
class CardOrderInfoServiceImpl : CardOrderInfoService {
    @Autowired
    private lateinit var levelUpSender: LevelUpSender
    @Autowired
    private lateinit var cardOrderInfoDao: CardOrderInfoDao

    @Autowired
    private lateinit var disProfiParamService: DisProfiParamService
    @Autowired
    private lateinit var disMemberInfoDao: DisMemberInfoDao

    private val log = LoggerFactory.getLogger(CardOrderInfoServiceImpl::class.java)

    override fun queryObject(id: String): CardOrderInfoEntity {
        return cardOrderInfoDao.queryObject(id)
    }

    override fun queryList(map: Map<String, Any>): List<CardOrderInfoEntity> {
        return cardOrderInfoDao.queryList(map)
    }


    @Throws(Exception::class)
    override fun save(cardOrderInfo: CardOrderInfoEntity) {
        cardOrderInfoDao.save(cardOrderInfo)
    }

    @Throws(Exception::class)
    override fun update(cardOrderInfo: CardOrderInfoEntity) {
        cardOrderInfoDao.update(cardOrderInfo)
    }

    @Throws(Exception::class)
    override fun delete(id: String) {
        cardOrderInfoDao.delete(id)
    }

    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        cardOrderInfoDao.deleteBatch(ids)
    }

    /**
     * 根据查询条件统计订单数量
     *
     * @param param
     * @return
     */
    override fun countOrder(param: Map<String, Any>): Int? {
        return if (MapUtils.isEmpty(param)) {
            0
        } else cardOrderInfoDao.countOrder(param)
    }

    /**
     * 统计用户办理的某张卡片的数量
     *
     * @param memberId
     * @param banNum
     * @return
     */
    override fun countUserCard(memberId: String, banNum: String): Int {
        if (StringUtils.isBlank(memberId) && StringUtils.isBlank(banNum)) {
            throw RRException("用户ID,银行卡编号不能为空")
        }
        val startDate = DateUtils.format(startDateOfMonth())
        val endDate = DateUtils.format(endDateOfMonth())
        return cardOrderInfoDao.countUserCard(memberId, banNum, startDate, endDate)
    }

    /**
     * 批量修改订单状态
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/27 15:20
     * @Description:
     */
    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun statusUpdate(map: Map<String, Any>) {
        cardOrderInfoDao.statusUpdate(map)
        val status = MapUtils.getIntValue(map, "orderStatus", 0)
        //订单成功后调用分润
        if (1 == status) {
            GlobalScope.launch {
                val cardOrderInfoEntityList = cardOrderInfoDao.queryListByIds(map["ids"] as List<*>)
                for (cardOrderInfoEntity in cardOrderInfoEntityList) {
                    val member = disMemberInfoDao.queryObject(cardOrderInfoEntity.memberInfo!!.id!!)
                    //如果当前办卡人和其上级都是非会员,则跳过分润
                    if ("0" == member.disUserType && "0" == member.disMemberParent!!.disUserType) {
                        continue
                    }
                    //调用分润
                    disProfiParamService.doFeeSplitting(member, cardOrderInfoEntity.cardInfo!!.rebate, false)
                    if ("0" == member.disMemberParent!!.disUserType) {
                        //执行会员升级
                        levelUpSender.send(JSON.toJSONString(member.disMemberParent))
                    }
                }
            }
        }
    }

}
