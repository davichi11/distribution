package com.distribution.modules.dis.service.impl;

import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.modules.account.dao.MemberAccountHistoryMapper;
import com.distribution.modules.account.dao.MemberAccountMapper;
import com.distribution.modules.account.entity.MemberAccount;
import com.distribution.modules.account.entity.MemberAccountHistory;
import com.distribution.modules.dis.dao.DisMemberInfoDao;
import com.distribution.modules.dis.dao.DisProfiParamMapper;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.entity.DisProfiParam;
import com.distribution.modules.dis.service.DisProfiParamService;
import com.distribution.weixin.service.WeiXinService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 分润服务实现类
 *
 * @author huchunliang
 */
@Slf4j
@Service("disProfiParamService")
public class DisProfiParamServiceImpl implements DisProfiParamService {
    @Autowired
    private DisProfiParamMapper disProfiParamMapper;
    @Autowired
    private MemberAccountMapper accountMapper;
    @Autowired
    private DisMemberInfoDao memberInfoDao;
    @Autowired
    private MemberAccountHistoryMapper historyMapper;
    @Autowired
    private WeiXinService weiXinService;

    @Override
    public DisProfiParam queryObject(String id) {
        return disProfiParamMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DisProfiParam> queryList(Map<String, Object> map) {
        return disProfiParamMapper.queryList(map);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DisProfiParam disProfiParam) throws Exception {
        disProfiParamMapper.insertSelective(disProfiParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DisProfiParam disProfiParam) throws Exception {
        disProfiParamMapper.updateByPrimaryKeySelective(disProfiParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        disProfiParamMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) throws Exception {
        disProfiParamMapper.deleteBatch(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doFeeSplitting(DisMemberInfoEntity member, Double money, boolean isReward) throws Exception {
        //获取分润参数配置
        Map<String, Object> profiMap = new HashMap<>(2);
        profiMap.put("disPlatformId", "1");
        List<DisProfiParam> disProfiParams = disProfiParamMapper.queryList(profiMap);
        disProfiParams.forEach(profiParam -> {
            //如果是百分比 转换分润值
            if ("0".equals(profiParam.getDisProMode())) {
                //会员推荐奖励 推荐人拿全款
                if (isReward) {
                    profiParam.setDisProValue(1.00);
                } else {
                    profiParam.setDisProValue(profiParam.getDisProValue() / 100);
                }
            }
        });
        //获取当前用户等级的分润
        DisProfiParam memberParam = disProfiParams.stream().filter(p -> p.getDisProLevel()
                .equals(member.getDisLevel().toString())).findFirst().orElse(new DisProfiParam());
        //当前用户账户信息
        MemberAccount memberAccount = accountMapper.selectMemberAccountByUserId(member.getId());
        if ("1".equals(member.getDisUserType())) {
            //当前用户分润,如果是购买会员则不分
            if (!isReward) {
                updateAccont(member, memberAccount, new BigDecimal(money * memberParam.getDisProValue()));
            }
        }
        //当前会员的上两级
        DisMemberInfoEntity parent = memberInfoDao.queryObject(member.getDisMemberParent().getId());
        MemberAccount parentAccount = accountMapper.selectMemberAccountByUserId(parent.getId());
        DisProfiParam parentParam = disProfiParams.stream().filter(p -> p.getDisProLevel()
                .equals(parent.getDisLevel().toString())).findFirst().orElse(new DisProfiParam());

        DisMemberInfoEntity grand = Optional.ofNullable(memberInfoDao.queryObject(parent.getParentId()))
                .orElse(new DisMemberInfoEntity());
        MemberAccount grandAccount = null;
        DisProfiParam grandParam = new DisProfiParam();
        if (grand.getId() != null) {
            grandAccount = accountMapper.selectMemberAccountByUserId(grand.getId());
            grandParam = disProfiParams.stream().filter(p -> p.getDisProLevel()
                    .equals(grand.getDisLevel().toString())).findFirst().orElse(new DisProfiParam());
        }
        if (isReward) {
            //上一级分润,用于直接购买会员
            updateParent(money, parent, parentAccount);
        } else {
            //上两级会员分润
            updateParentAndGrand(member, money, memberParam.getDisProValue(), parent, parentAccount, parentParam.getDisProValue(),
                    grand, grandAccount, grandParam.getDisProValue());

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
    private void updateParent(Double money, DisMemberInfoEntity parent, MemberAccount parentAccount) throws Exception {
        if ("1".equals(parent.getDisUserType())) {
            updateAccont(parent, parentAccount, new BigDecimal(money));
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
    private void updateParentAndGrand(DisMemberInfoEntity member, Double money, Double memberParam,
                                      DisMemberInfoEntity parent, MemberAccount parentAccount, Double parentParam,
                                      DisMemberInfoEntity grand, MemberAccount grandAccount, Double grandParam) throws Exception {
        //当三人都在同级时,当前会员拿级别佣金,上一级拿5%
        if (member.getDisLevel().equals(parent.getDisLevel()) && grand != null && member.getDisLevel().equals(grand.getDisLevel())) {
            updateAccont(parent, parentAccount, new BigDecimal(money * 0.05));
        } else {
            //当前会员等级低于上级时
            if (isUpLevel(member, parent)) {
                //当办卡人向上两级都在同一级别时办卡人拿级别佣金、向上一级拿差价佣金、向上二级拿5%
                if (grand != null && parent.getDisLevel().equals(grand.getDisLevel())) {
                    BigDecimal parentMoney = new BigDecimal(money * (parentParam - memberParam));
                    updateAccont(parent, parentAccount, parentMoney);
                    if (grandAccount != null) {
                        updateAccont(grand, grandAccount, new BigDecimal(money * 0.05));
                    }
                } else { //当办卡人和以上2级都不在同级时各自拿各自的佣金比例
                    BigDecimal parentMoney = new BigDecimal(money * (parentParam - memberParam));
                    updateAccont(parent, parentAccount, parentMoney);
                    //当第二级会员等级高于当前会员等级,并且高于第一级会员时,第二级级会员拿与第一级会员的差价佣金
                    if (grandAccount != null && isUpLevel(member, grand) && isUpLevel(parent, grand)) {
                        BigDecimal grandMoney = new BigDecimal(money * (grandParam - parentParam));
                        updateAccont(grand, grandAccount, grandMoney);
                    }
                }
            } else {
                //当办卡人超越第一级时、办卡人拿级别拥挤、第一级不拿佣金，第二级拿与办卡人的差价佣金
                if (grand != null && isUpLevel(parent, member) && isUpLevel(member, grand)) {
                    BigDecimal grandMoney = new BigDecimal(money * (grandParam - memberParam));
                    updateAccont(grand, grandAccount, grandMoney);
                }
                //当办卡人与第一级同级时、第一级拿5%，第二级高于第一级时拿与办卡人的差价佣金否则不拿
                if (parent.getDisLevel().equals(member.getDisLevel())) {
                    updateAccont(parent, parentAccount, new BigDecimal(money * 0.05));
                    if (grandAccount != null && isUpLevel(parent, grand)) {
                        updateAccont(grand, grandAccount, new BigDecimal(money * (grandParam - memberParam)));
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
    private boolean isUpLevel(DisMemberInfoEntity child, DisMemberInfoEntity parent) {
        return "1".equals(parent.getDisUserType()) && "0".equals(child.getDisUserType()) || child.getDisLevel() > parent.getDisLevel();
    }

    /**
     * 更新账户
     *
     * @param member
     * @param account
     * @param money
     */
    private void updateAccont(DisMemberInfoEntity member, MemberAccount account, BigDecimal money) throws Exception {
        account.setMemberAmount(account.getMemberAmount().add(money));
        accountMapper.updateByPrimaryKeySelective(account);
        //保存交易记录
        MemberAccountHistory history = new MemberAccountHistory();
        history.setId(CommonUtils.getUUID());
        history.setAccountId(account.getAccountId());
        history.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
        history.setHisAmount(money);
        history.setHisType(MemberAccountHistory.HisType.INCOME);
        historyMapper.insert(history);
        //公众号给用户发送消息
        WxMpTemplateMessage templateMessage = buildTemplateMsg(member.getOpenId(), money,
                member.getDisUserName(), account.getMemberAmount());
        weiXinService.sendTemplateMsg(templateMessage);
    }

    /**
     * 组装模板消息
     *
     * @param openId
     * @param money   返现金额
     * @param name    用户名
     * @param balance 账户余额
     * @return
     */
    private WxMpTemplateMessage buildTemplateMsg(String openId, BigDecimal money, String name, BigDecimal balance) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("8PktExsPLnwT5Z6IXFuTsMhLIHzfY0m4MZxqBbeQvEg");
        wxMpTemplateMessage.setToUser(openId);
        List<WxMpTemplateData> templateDataList = Lists.newArrayList(
                new WxMpTemplateData("first", MessageFormat.format("尊敬的{0}您好，您获得了一笔新的佣金！", name)),
                new WxMpTemplateData("keyword1", money.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()),
                new WxMpTemplateData("keyword2", DateUtils.formatDateTime(LocalDateTime.now())),
                new WxMpTemplateData("remark", "感谢您的使用")
        );
        wxMpTemplateMessage.setData(templateDataList);
        return wxMpTemplateMessage;
    }

}
