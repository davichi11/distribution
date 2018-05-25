package com.distribution.modules.dis.service.impl;

import com.distribution.modules.dis.dao.DisProfiParamMapper;
import com.distribution.modules.dis.entity.DisProfiParam;
import com.distribution.modules.dis.service.DisProfiParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 分润服务实现类
 * @author huchunliang
 */
@Service("disProfiParamService")
public class DisProfiParamServiceImpl implements DisProfiParamService {
    @Autowired
    private DisProfiParamMapper disProfiParamMapper;

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
    public void save(DisProfiParam disProfiParam) throws Exception{
        disProfiParamMapper.insertSelective(disProfiParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DisProfiParam disProfiParam) throws Exception{
        disProfiParamMapper.updateByPrimaryKeySelective(disProfiParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception{
        disProfiParamMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[]ids) throws Exception{
        disProfiParamMapper.deleteBatch(ids);
    }

}
