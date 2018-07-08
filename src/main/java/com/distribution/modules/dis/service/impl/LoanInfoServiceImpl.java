package com.distribution.modules.dis.service.impl;

import com.distribution.modules.dis.dao.LoanInfoDao;
import com.distribution.modules.dis.entity.LoanInfoEntity;
import com.distribution.modules.dis.service.LoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("loanInfoService")
public class LoanInfoServiceImpl implements LoanInfoService {
    @Autowired
    private LoanInfoDao loanInfoDao;

    @Override
    public LoanInfoEntity queryObject(String id) {
        return loanInfoDao.queryObject(id);
    }

    @Override
    public List<LoanInfoEntity> queryList(Map<String, Object> map) {
        return loanInfoDao.queryList(map);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(LoanInfoEntity loanInfo) throws Exception {
        loanInfoDao.save(loanInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LoanInfoEntity loanInfo) throws Exception {
        loanInfoDao.update(loanInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        loanInfoDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) throws Exception {
        loanInfoDao.deleteBatch(ids);
    }

}
