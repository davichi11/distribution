package com.distribution.modules.dis.service.impl;

import com.alibaba.fastjson.JSON;
import com.distribution.modules.dis.dao.DisMemberInfoDao;
import com.distribution.modules.dis.dao.LoanOrderInfoDao;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.entity.LoanOrderInfoEntity;
import com.distribution.modules.dis.service.DisProfiParamService;
import com.distribution.modules.dis.service.LoanOrderInfoService;
import com.distribution.queue.LevelUpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("loanOrderInfoService")
public class LoanOrderInfoServiceImpl implements LoanOrderInfoService {
    @Autowired
    private LoanOrderInfoDao loanOrderInfoDao;
    @Autowired
    private DisMemberInfoDao disMemberInfoDao;
    @Autowired
    private DisProfiParamService disProfiParamService;
    @Autowired
    private LevelUpSender levelUpSender;

    @Override
    public LoanOrderInfoEntity queryObject(String id) {
        return loanOrderInfoDao.queryObject(id);
    }

    @Override
    public List<LoanOrderInfoEntity> queryList(Map<String, Object> map) {
        return loanOrderInfoDao.queryList(map);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(LoanOrderInfoEntity loanOrderInfo) throws Exception {
        loanOrderInfoDao.save(loanOrderInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LoanOrderInfoEntity loanOrderInfo) throws Exception {
        //这里要调取分润
        loanOrderInfoDao.update(loanOrderInfo);
        //申请成功,执行分润
        if (loanOrderInfo.getOrderStatus() == 1) {
            DisMemberInfoEntity member = disMemberInfoDao.queryByMobile(loanOrderInfo.getOrderMobile());
            //如果当前办卡人和其上级都是非会员,则跳过分润
            if ("0".equals(member.getDisUserType()) && "0".equals(member.getDisMemberParent().getDisUserType())) {
                return;
            }
            //调用分润
            disProfiParamService.doFeeSplitting(member, loanOrderInfo.getLoanMoney(), false);
            if ("0".equals(member.getDisMemberParent().getDisUserType())) {
                //执行会员升级
                levelUpSender.send(JSON.toJSONString(member.getDisMemberParent()));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        loanOrderInfoDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) throws Exception {
        loanOrderInfoDao.deleteBatch(ids);
    }

}
