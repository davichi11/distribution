package com.distribution.modules.api.service.impl

import com.distribution.common.exception.RRException
import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.DateUtils
import com.distribution.modules.account.dao.MemberAccountMapper
import com.distribution.modules.account.entity.MemberAccount
import com.distribution.modules.api.dao.UserDao
import com.distribution.modules.api.entity.UserEntity
import com.distribution.modules.api.service.UserService
import com.distribution.modules.dis.dao.DisFansMapper
import com.distribution.modules.dis.dao.DisMemberInfoDao
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*


@Service("userService")
class UserServiceImpl : UserService {
    @Autowired
    private lateinit var userDao: UserDao
    @Autowired
    private lateinit var memberInfoDao: DisMemberInfoDao
    @Autowired
    private lateinit var fansMapper: DisFansMapper
    @Autowired
    private lateinit var memberAccountMapper: MemberAccountMapper

    override fun queryObject(userId: String): UserEntity {
        return userDao.queryObject(userId)
    }

    override fun queryByMemberId(memberId: String): UserEntity {
        return userDao.queryByMemberId(memberId)
    }

    override fun queryList(map: Map<String, Any>): List<UserEntity> {
        return userDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return userDao.queryTotal(map)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(mobile: String, password: String?, name: String?, idCode: String?,
                      openId: String?, fatherWorkerId: String?): UserEntity {
        val user = UserEntity()
        user.userId = CommonUtils.uuid
        user.mobile = mobile
        user.username = name
        user.password = DigestUtils.sha256Hex(password)
        user.createTime = LocalDateTime.now()
        user.userId = CommonUtils.uuid
        userDao.save(user)
        //添加会员信息
        val member = DisMemberInfoEntity()
        member.id = CommonUtils.uuid
        member.disUserName = name
        member.idCode = idCode
        member.userEntity = user
        member.openId = openId
        //是否已是锁粉
        val disFansList = fansMapper.selectList(if (openId == null) mapOf() else mapOf("openId" to openId))
        when {
            disFansList.isNotEmpty() -> {
                val fans = disFansList[0]
                member.parentId = fans.disMemberInfo!!.parentId
                member.disMemberParent = fans.disMemberInfo!!
                member.disPlatformId = 1L
            }
            StringUtils.isNoneBlank(fatherWorkerId) -> {
                val father = memberInfoDao.queryByWorkerId(NumberUtils.toLong(fatherWorkerId))!!
                member.parentId = father.id
                member.disMemberParent = father
                member.disPlatformId = 1L
            }
        }
        member.disLevel = 0
        member.disUserType = "0"
        member.addTime = DateUtils.formatDateTime(LocalDateTime.now())
        memberInfoDao.save(member)
        user.memberInfo = member


        //注册成功之后开通默认账户
        val memberAccount = MemberAccount()
        memberAccount.member = member
        memberAccount.accountId = CommonUtils.uuid
        memberAccount.memberType = "1"
        memberAccountMapper.insertSelective(memberAccount)

        return user
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(user: UserEntity) {
        userDao.update(user)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(userId: Long) {
        userDao.delete(userId)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(userIds: Array<Long>) {
        userDao.deleteBatch(userIds)
    }

    override fun queryByMobile(mobile: String): UserEntity {
        return userDao.queryByMobile(mobile)
    }

    override fun login(mobile: String, password: String): String {
        val user = Optional.ofNullable(queryByMobile(mobile)).filter { u -> u.password == DigestUtils.sha256Hex(password) }
                .orElseThrow { RRException("手机号或密码错误") }
        return user.userId!!
    }

}
