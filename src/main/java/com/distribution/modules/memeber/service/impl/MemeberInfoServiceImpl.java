package com.distribution.modules.memeber.service.impl;

import com.distribution.modules.memeber.dao.MemeberInfoMapper;
import com.distribution.modules.memeber.entity.MemeberInfo;
import com.distribution.modules.memeber.service.MemeberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class MemeberInfoServiceImpl implements MemeberInfoService {

    @Autowired
    private MemeberInfoMapper memeberInfoMapper;

    @Override
    public MemeberInfo queryObject(String id) {
        return memeberInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MemeberInfo> queryList(Map<String, Object> map) {
        return memeberInfoMapper.selectList(map);
    }

    @Override
    public void save(MemeberInfo memeberInfo) throws Exception {
        memeberInfoMapper.insert(memeberInfo);
    }

    @Override
    public void update(MemeberInfo memeberInfo) throws Exception {
        memeberInfoMapper.updateByPrimaryKeySelective(memeberInfo);
    }

    @Override
    public void delete(String id) throws Exception {
        memeberInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(String[] ids) throws Exception {
        memeberInfoMapper.deleteBatch(ids);
    }
}
