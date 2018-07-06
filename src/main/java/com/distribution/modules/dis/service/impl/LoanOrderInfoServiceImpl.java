package com.distribution.modules.dis.service.impl;

import com.alibaba.fastjson.JSON;
import com.distribution.modules.dis.dao.DisMemberInfoDao;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.DisProfiParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.distribution.modules.dis.dao.LoanOrderInfoDao;
import com.distribution.modules.dis.entity.LoanOrderInfoEntity;
import com.distribution.modules.dis.service.LoanOrderInfoService;
import org.springframework.transaction.annotation.Transactional;


@Service("loanOrderInfoService")
public class LoanOrderInfoServiceImpl implements LoanOrderInfoService {
    @Autowired
    private LoanOrderInfoDao loanOrderInfoDao;
    @Autowired
    private DisMemberInfoDao disMemberInfoDao;
    @Autowired
    private DisProfiParamService disProfiParamService;
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
    public void save(LoanOrderInfoEntity loanOrderInfo) throws Exception{
            loanOrderInfoDao.save(loanOrderInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LoanOrderInfoEntity loanOrderInfo) throws Exception{
        //这里要调取分润
        loanOrderInfoDao.update(loanOrderInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception{
            loanOrderInfoDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[]ids) throws Exception{
            loanOrderInfoDao.deleteBatch(ids);
    }

}