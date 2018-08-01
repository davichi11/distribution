package com.distribution.modules.dis.service.impl

import com.distribution.modules.api.dao.UserDao
import com.distribution.modules.dis.dao.CardOrderInfoDao
import com.distribution.modules.dis.dao.DisFansMapper
import com.distribution.modules.dis.dao.DisMemberInfoDao
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.service.DisMemberInfoService
import org.apache.commons.collections.CollectionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class DisMemberInfoServiceImpl : DisMemberInfoService {

    @Autowired
    private lateinit var disMemberInfoDao: DisMemberInfoDao
    @Autowired
    private lateinit var cardOrderInfoDao: CardOrderInfoDao
    @Autowired
    private lateinit var disFansMapper: DisFansMapper
    @Autowired
    private lateinit var userDao: UserDao

    override fun queryByPage(map: Map<String, Any>): List<DisMemberInfoEntity>? {
        return disMemberInfoDao.queryForPage(map)
    }

    override fun queryObject(id: String): DisMemberInfoEntity {
        return disMemberInfoDao.queryObject(id)
    }

    override fun queryByOpenId(openId: String): DisMemberInfoEntity {
        return disMemberInfoDao.findByOpenId(openId)
    }

    override fun queryByMobile(mobile: String): DisMemberInfoEntity {
        return disMemberInfoDao.queryByMobile(mobile)
    }

    override fun queryList(map: Map<String, Any>): List<DisMemberInfoEntity> {
        return disMemberInfoDao.queryList(map)
    }

    @Throws(Exception::class)
    override fun save(disMemberInfo: DisMemberInfoEntity) {
        disMemberInfoDao.save(disMemberInfo)
    }

    @Throws(Exception::class)
    override fun update(disMemberInfo: DisMemberInfoEntity) {
        disMemberInfoDao.update(disMemberInfo)
    }

    @Throws(Exception::class)
    override fun updateDisLevel(level: Int?, type: String, id: String) {
        disMemberInfoDao.updateDisLevel(level, type, id)
    }

    @Throws(Exception::class)
    override fun delete(id: String) {
        disMemberInfoDao.delete(id)
    }

    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        disMemberInfoDao.deleteBatch(ids)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun levelUp(memberInfo: DisMemberInfoEntity): Boolean {
        //如果是非会员升级,查询其下线完成的订单和锁粉信息,大于三个并且锁粉达到10个可以升级为三级会员
        if ("0" == memberInfo.disUserType) {
            //查询锁粉数据
            val param = HashMap<String, Any>(2)
            param["memberId"] = memberInfo.id!!
            val disFansList = disFansMapper.selectList(param)

            if (disFansList.size >= 10) {
                val memberParam = HashMap<String, Any>()
                memberParam["openIds"] = disFansList.map { it.wechatId }.toList()
                val fansMemberIds = disMemberInfoDao.queryList(memberParam).map { it.id }.toList()
                if (CollectionUtils.isNotEmpty(fansMemberIds)) {
                    memberParam["memberIds"] = fansMemberIds
                }
                val count = cardOrderInfoDao.queryList(memberParam)
                        .filter { order -> order.orderStatus == 1 }.count()
                //
                if (count >= 3) {
                    memberInfo.disUserType = "1"
                    memberInfo.disLevel = 3
                    val userEntity = userDao.queryByMemberId(memberInfo.id!!)
                    memberInfo.userEntity = userEntity
                    update(memberInfo)
                    return parentLevelUp(memberInfo.disMemberParent!!)
                }
            }
        } else if ("1" == memberInfo.disUserType) {
            return parentLevelUp(memberInfo)
        }

        return false
    }

    /**
     * 递归升级会员
     *
     * @param member
     * @return
     */
    @Throws(Exception::class)
    private fun parentLevelUp(member: DisMemberInfoEntity): Boolean {
        val parent = disMemberInfoDao.queryObject(member.id!!)
        if (member.disLevel == 1) {
            return true
        }
        //查询会员的下级会员数量
        val param = HashMap<String, Any>(2)
        param["parent_id"] = member.id!!
        val children = disMemberInfoDao.queryList(param).filter { m -> "1" == m.disUserType }.count()
        //三级升二级
        if (member.disLevel == 3 && children >= 5) {
            disMemberInfoDao.updateDisLevel(2, "1", member.id!!)

        }
        //二级升一级
        if (member.disLevel == 2 && children >= 15) {
            disMemberInfoDao.updateDisLevel(1, "1", member.id!!)
        }
        return parentLevelUp(parent.disMemberParent!!)
    }


}
