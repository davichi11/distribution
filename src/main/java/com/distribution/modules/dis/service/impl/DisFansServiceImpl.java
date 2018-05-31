package com.distribution.modules.dis.service.impl;

import com.distribution.modules.dis.dao.DisFansMapper;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.service.DisFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.dis.service.impl
 * @Description TODO(描述)
 * @create 2018/5/11-23:06
 */
@Service
public class DisFansServiceImpl implements DisFansService {
    @Autowired
    private DisFansMapper disFansMapper;

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public DisFans queryObject(String id) {
        return disFansMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    @Override
    public List<DisFans> queryList(Map<String, Object> map) {
        return disFansMapper.selectList(map);
    }

    /**
     * 保存
     *
     * @param disFans
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DisFans disFans) throws Exception {
        disFansMapper.insertSelective(disFans);
    }

    /**
     * 更新
     *
     * @param disFans
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DisFans disFans) throws Exception {
        disFansMapper.updateByPrimaryKeySelective(disFans);
    }

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        disFansMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) throws Exception {
        disFansMapper.deleteBatch(ids);
    }
}
