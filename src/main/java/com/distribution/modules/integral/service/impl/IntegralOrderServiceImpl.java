package com.distribution.modules.integral.service.impl;

import com.distribution.modules.integral.dao.IntegralOrderDao;
import com.distribution.modules.integral.entity.IntegralOrderEntity;
import com.distribution.modules.integral.service.IntegralOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("integralOrderService")
public class IntegralOrderServiceImpl implements IntegralOrderService {
    @Autowired
    private IntegralOrderDao integralOrderDao;

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
