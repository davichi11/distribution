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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        //获取当前用户等级的分润
        DisProfiParam memberParam = disProfiParams.stream().filter(p -> p.getDisProLevel()
                .equals(member.getDisLevel().toString())).findFirst().orElse(new DisProfiParam());
        //如果是百分比 转换分润值
        if ("0".equals(memberParam.getDisProMode())) {
            //会员推荐奖励 推荐人拿全款
            if (isReward) {
                memberParam.setDisProValue(100.00);
            } else {
                memberParam.setDisProValue(memberParam.getDisProValue() / 100);
            }
        }
        //当前用户账户信息
        //当前用户分润
        MemberAccount memberAccount = accountMapper.selectMemberAccountByUserId(member.getId());
        updateAccont(member, memberAccount, new BigDecimal(money * memberParam.getDisProValue()));
        //当前会员的上两级
        DisMemberInfoEntity parent = member.getDisMemberParent();
        MemberAccount parentAccount = accountMapper.selectMemberAccountByUserId(parent.getId());
        DisProfiParam parentParam = disProfiParams.stream().filter(p -> p.getDisProLevel()
                .equals(parent.getDisLevel().toString())).findFirst().orElse(new DisProfiParam());

        DisMemberInfoEntity grand = memberInfoDao.queryObject(parent.getParentId());
        MemberAccount grandAccount = null;
        DisProfiParam grandParam = null;
        if (grand != null) {
            grandAccount = accountMapper.selectMemberAccountByUserId(grand.getId());
            grandParam = disProfiParams.stream().filter(p -> p.getDisProLevel()
                    .equals(grand.getDisLevel().toString())).findFirst().orElse(new DisProfiParam());
        }
        //上两级会员分润
        //当当前会员与上两级都在同级时,当前会员拿级别佣金,上一级拿5%
        if (member.getDisLevel().equals(parent.getDisLevel()) && grand != null && member.getDisLevel().equals(grand.getDisLevel())) {
            updateAccont(parent, parentAccount, new BigDecimal(money * 0.05));
        } else {
            //当前会员等级低于上级时
            if (member.getDisLevel() > parent.getDisLevel()) {
                //当办卡人向上两级都在同一级别时办卡人拿级别佣金、向上一级拿差价佣金、向上二级拿5%
                if (grand != null && parent.getDisLevel().equals(grand.getDisLevel())) {
                    BigDecimal parentMoney = new BigDecimal(money * (parentParam.getDisProValue() - memberParam.getDisProValue()));
                    updateAccont(parent, parentAccount, parentMoney);
                    if (grandAccount != null) {
                        updateAccont(grand, grandAccount, new BigDecimal(money * 0.05));
                    }
                } else { //当办卡人和以上2级都不在同级时各自拿各自的佣金比例
                    BigDecimal parentMoney = new BigDecimal(money * (parentParam.getDisProValue() - memberParam.getDisProValue()));
                    updateAccont(parent, parentAccount, parentMoney);
                    if (grandAccount != null) {
                        BigDecimal grandMoney = new BigDecimal(money * (grandParam.getDisProValue() - parentParam.getDisProValue()));
                        updateAccont(grand, grandAccount, grandMoney);
                    }
                }
            } else { //当办卡人超越第一级时、办卡人拿级别拥挤、第一级不拿佣金，第二级拿与办卡人的差价佣金
                if (grand != null) {
                    BigDecimal grandMoney = new BigDecimal(money * (grandParam.getDisProValue() - memberParam.getDisProValue()));
                    updateAccont(grand, grandAccount, grandMoney);
                }
            }
        }
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
    }

}
