package com.distribution.modules.integral.service.impl;

import com.alibaba.fastjson.JSON;
import com.distribution.modules.dis.dao.DisMemberInfoDao;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.DisProfiParamService;
import com.distribution.modules.integral.dao.IntegralOrderDao;
import com.distribution.modules.integral.entity.IntegralOrderEntity;
import com.distribution.modules.integral.service.IntegralOrderService;
import com.distribution.queue.LevelUpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("integralOrderService")
public class IntegralOrderServiceImpl implements IntegralOrderService {
    @Autowired
    private IntegralOrderDao integralOrderDao;
    @Autowired
    private DisProfiParamService disProfiParamService;
    @Autowired
    private DisMemberInfoDao disMemberInfoDao;
    @Autowired
    private LevelUpSender levelUpSender;

    @Override
    public IntegralOrderEntity queryObject(String id) {
        return integralOrderDao.queryObject(id);
    }

    @Override
    public List<IntegralOrderEntity> queryList(Map<String, Object> map) {
        return integralOrderDao.queryList(map);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(IntegralOrderEntity integralOrder) throws Exception {
        integralOrderDao.save(integralOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(IntegralOrderEntity integralOrder) throws Exception {
        integralOrderDao.update(integralOrder);
        //申请成功,执行分润
        if (integralOrder.getStatus() == 1) {
            DisMemberInfoEntity member = disMemberInfoDao.queryByMobile(integralOrder.getMobile().toString());
            //如果当前办卡人和其上级都是非会员,则跳过分润
            if ("0".equals(member.getDisUserType()) && "0".equals(member.getDisMemberParent().getDisUserType())) {
                return;
            }
            //调用分润
            disProfiParamService.doFeeSplitting(member, integralOrder.getProfiMoney(), false);
            if ("0".equals(member.getDisMemberParent().getDisUserType())) {
                //执行会员升级
                levelUpSender.send(JSON.toJSONString(member.getDisMemberParent()));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        integralOrderDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) throws Exception {
        integralOrderDao.deleteBatch(ids);
    }

}
