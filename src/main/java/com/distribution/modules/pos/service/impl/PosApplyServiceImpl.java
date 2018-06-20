package com.distribution.modules.pos.service.impl;

import com.distribution.modules.pos.dao.PosApplyDao;
import com.distribution.modules.pos.entity.PosApplyEntity;
import com.distribution.modules.pos.service.PosApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("posApplyService")
public class PosApplyServiceImpl implements PosApplyService {
    @Autowired
    private PosApplyDao posApplyDao;

    @Override
    public PosApplyEntity queryObject(String id) {
        return posApplyDao.queryObject(id);
    }

    @Override
    public List<PosApplyEntity> queryList(Map<String, Object> map) {
        return posApplyDao.queryList(map);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(PosApplyEntity PosApply) throws Exception {
        posApplyDao.save(PosApply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PosApplyEntity PosApply) throws Exception {
        posApplyDao.update(PosApply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        posApplyDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) throws Exception {
        posApplyDao.deleteBatch(ids);
    }

}
