package com.distribution.modules.dis.service.impl

import com.distribution.common.utils.CommonUtils
import com.distribution.common.utils.DateUtils
import com.distribution.modules.account.dao.MemberAccountHistoryMapper
import com.distribution.modules.account.dao.MemberAccountMapper
import com.distribution.modules.account.entity.MemberAccount
import com.distribution.modules.account.entity.MemberAccountHistory
import com.distribution.modules.dis.dao.DisMemberInfoDao
import com.distribution.modules.dis.dao.DisProfiParamMapper
import com.distribution.modules.dis.dao.DisProfitRecordMapper
import com.distribution.modules.dis.entity.DisMemberInfoEntity
import com.distribution.modules.dis.entity.DisProfiParam
import com.distribution.modules.dis.entity.DisProfitRecord
import com.distribution.modules.dis.service.DisProfiParamService
import com.distribution.weixin.service.WeiXinService
import com.distribution.weixin.utils.WxUtils
import com.google.common.collect.Lists
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.text.MessageFormat
import java.time.LocalDateTime
import java.util.*


/**
 * 分润服务实现类
 *
 * @author huchunliang
 */

@Service("disProfiParamService")
class DisProfiParamServiceImpl : DisProfiParamService {
    @Autowired
    private lateinit var disProfiParamMapper: DisProfiParamMapper
    @Autowired
    private lateinit var accountMapper: MemberAccountMapper
    @Autowired
    private lateinit var memberInfoDao: DisMemberInfoDao
    @Autowired
    private lateinit var historyMapper: MemberAccountHistoryMapper
    @Autowired
    private lateinit var disProfitRecordMapper: DisProfitRecordMapper
    @Autowired
    private lateinit var weiXinService: WeiXinService

    override fun queryObject(id: String): DisProfiParam {
        return disProfiParamMapper.selectByPrimaryKey(id)
    }

    override fun queryList(map: Map<String, Any>): List<DisProfiParam> {
        return disProfiParamMapper.queryList(map)
    }


    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(disProfiParam: DisProfiParam) {
        disProfiParamMapper.insertSelective(disProfiParam)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(disProfiParam: DisProfiParam) {
        disProfiParamMapper.updateByPrimaryKeySelective(disProfiParam)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun delete(id: String) {
        disProfiParamMapper.deleteByPrimaryKey(id)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<String>) {
        disProfiParamMapper.deleteBatch(ids)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun doFeeSplitting(member: DisMemberInfoEntity, money: Double, isReward: Boolean) {
        //获取分润参数配置
        val profiMap = HashMap<String, Any>(2)
        profiMap["disPlatformId"] = "1"
        val disProfiParams = disProfiParamMapper.queryList(profiMap)
        disProfiParams.forEach { profiParam ->
            //如果是百分比 转换分润值
            if ("0" == profiParam.disProMode) {
                //会员推荐奖励 推荐人拿全款
                when {
                    isReward -> profiParam.disProValue = 1.00
                    else -> profiParam.disProValue = profiParam.disProValue / 100
                }
            }
        }
        //获取当前用户等级的分润
        val memberParam = if ("0" == member.disUserType) DisProfiParam() else disProfiParams.first { p -> p.disProLevel == member.disLevel.toString() }
        //当前用户账户信息
        val memberAccount = accountMapper.selectMemberAccountByUserId(member.id!!)
        //当前用户分润,如果是购买会员则不分
        if ("1" == member.disUserType && !isReward) {
            updateAccont(member, memberAccount, BigDecimal(money * memberParam.disProValue))
        }
        //当前会员的上一级
        val parent = memberInfoDao.queryObject(member.disMemberParent!!.id!!)
        val parentAccount = accountMapper.selectMemberAccountByUserId(parent.id!!)
        val parentParam = disProfiParams.first { p -> p.disProLevel == parent.disLevel.toString() }
        if (isReward) {
            //上一级分润,用于直接购买会员
            updateParent(money, parent, parentAccount)
        } else {
            //上两级会员
            val grand = if (parent.parentId != null) memberInfoDao.queryObject(parent.parentId!!) else null
            //上两级会员账户
            val grandAccount = if (grand != null) accountMapper.selectMemberAccountByUserId(grand.id!!) else null
            //上两级会员分润参数
            val grandParam = if (grand != null)
                disProfiParams.first { p -> p.disProLevel == grand.disLevel.toString() }
            else DisProfiParam()

            //上两级会员分润
            updateParentAndGrand(member, money, memberParam.disProValue, parent, parentAccount, parentParam.disProValue,
                    grand, grandAccount, grandParam.disProValue)

        }


    }

    /**
     * 上一级分润,用于直接购买会员
     *
     * @param money
     * @param parent
     * @param parentAccount
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun updateParent(money: Double, parent: DisMemberInfoEntity, parentAccount: MemberAccount?) {
        if ("1" == parent.disUserType) {
            updateAccont(parent, parentAccount, BigDecimal(money))
        }
    }

    /**
     * 上两级会员分润
     *
     * @param member        当前会员
     * @param money         总金额
     * @param memberParam   当前会员分润值
     * @param parent        上级会员
     * @param parentAccount 上级会员账户
     * @param parentParam   上级会员分润值
     * @param grand         上两级会员
     * @param grandAccount  上两级会员账户
     * @param grandParam    上两级会员分润值
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun updateParentAndGrand(member: DisMemberInfoEntity, money: Double, memberParam: Double,
                                     parent: DisMemberInfoEntity, parentAccount: MemberAccount?, parentParam: Double?,
                                     grand: DisMemberInfoEntity?, grandAccount: MemberAccount?, grandParam: Double?) {
        //当三人都在同级时,当前会员拿级别佣金,上一级拿5%
        if (member.disLevel == parent.disLevel && grand != null && member.disLevel == grand.disLevel) {
            updateAccont(parent, parentAccount, BigDecimal(money * 0.05))
        } else {
            //当前会员等级低于上级时
            if (isUpLevel(member, parent)) {
                //当办卡人向上两级都在同一级别时办卡人拿级别佣金、向上一级拿差价佣金、向上二级拿5%
                if (grand != null && parent.disLevel == grand.disLevel) {
                    val parentMoney = BigDecimal(money * (parentParam!! - memberParam))
                    updateAccont(parent, parentAccount, parentMoney)
                    if (grandAccount != null) {
                        updateAccont(grand, grandAccount, BigDecimal(money * 0.05))
                    }
                } else { //当办卡人和以上2级都不在同级时各自拿各自的佣金比例
                    val parentMoney = BigDecimal(money * (parentParam!! - memberParam))
                    updateAccont(parent, parentAccount, parentMoney)
                    //当第二级会员等级高于当前会员等级,并且高于第一级会员时,第二级级会员拿与第一级会员的差价佣金
                    if (grandAccount != null && isUpLevel(member, grand!!) && isUpLevel(parent, grand)) {
                        val grandMoney = BigDecimal(money * (grandParam!! - parentParam))
                        updateAccont(grand, grandAccount, grandMoney)
                    }
                }
            } else {
                //当办卡人超越第一级时、办卡人拿级别拥挤、第一级不拿佣金，第二级拿与办卡人的差价佣金
                if (grand != null && isUpLevel(parent, member) && isUpLevel(member, grand)) {
                    val grandMoney = BigDecimal(money * (grandParam!! - memberParam))
                    updateAccont(grand, grandAccount!!, grandMoney)
                }
                //当办卡人与第一级同级时、第一级拿5%，第二级高于第一级时拿与办卡人的差价佣金否则不拿
                if (parent.disLevel == member.disLevel) {
                    updateAccont(parent, parentAccount, BigDecimal(money * 0.05))
                    if (grandAccount != null && isUpLevel(parent, grand!!)) {
                        updateAccont(grand, grandAccount, BigDecimal(money * (grandParam!! - memberParam)))
                    }
                }
            }
        }
    }

    /**
     * 是否是上级会员
     *
     * @param child
     * @param parent
     * @return
     */
    private fun isUpLevel(child: DisMemberInfoEntity, parent: DisMemberInfoEntity): Boolean {
        return "1" == parent.disUserType && "0" == child.disUserType || child.disLevel!! > parent.disLevel!!
    }

    /**
     * 更新账户
     *
     * @param member
     * @param account
     * @param money
     */
    @Throws(Exception::class)
    private fun updateAccont(member: DisMemberInfoEntity, account: MemberAccount?, money: BigDecimal) {
        if (account == null) {
            return
        }
        account.memberAmount = account.memberAmount.add(money)
        accountMapper.updateByPrimaryKeySelective(account)
        //保存交易记录
        val history = MemberAccountHistory()
        history.id = CommonUtils.uuid
        history.accountId = account.accountId
        history.addTime = DateUtils.formatDateTime(LocalDateTime.now())
        history.hisAmount = money
        history.hisType = MemberAccountHistory.HisType.INCOME
        historyMapper.insert(history)
        //保存分润记录
        val profitRecord = DisProfitRecord()
        profitRecord.disGetUserId = member.id!!
        profitRecord.disSetUserId = member.disMemberParent?.id ?: ""
        profitRecord.disAmount = money
        profitRecord.disProType = "1"
        profitRecord.disNote = ""
        profitRecord.account = account.aliPayAccount
        profitRecord.disOrderId = ""
        profitRecord.addTime = DateUtils.formatDateTime(LocalDateTime.now())
        profitRecord.id = CommonUtils.uuid
        disProfitRecordMapper.insert(profitRecord)
        GlobalScope.launch {
            //公众号给用户发送消息
//            val templateMessage = buildTemplateMsg(member.openId, money,
//                    member.disUserName, account.memberAmount)
            val templateDataList = Lists.newArrayList(
                    WxMpTemplateData("first", MessageFormat.format("尊敬的{0}您好，您获得了一笔新的佣金！", member.disUserName)),
                    WxMpTemplateData("keyword1", money.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()),
                    WxMpTemplateData("keyword2", DateUtils.formatDateTime(LocalDateTime.now())),
                    WxMpTemplateData("remark", "感谢您的使用")
            )
            WxUtils.buildAndSendTemplateMsg(member.openId!!, "8PktExsPLnwT5Z6IXFuTsMhLIHzfY0m4MZxqBbeQvEg",
                    templateDataList, weiXinService)
        }
    }

}
