package com.distribution.modules.card.service.impl;

import com.distribution.modules.card.dao.CardInfoMapper;
import com.distribution.modules.card.entity.CardInfo;
import com.distribution.modules.card.service.CardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.card.service.impl
 * @Description TODO(描述)
 * @create 2018/5/8-20:23
 */
@Service
public class CardInfoServiceImpl implements CardInfoService {

    @Autowired
    private CardInfoMapper cardInfoMapper;

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public CardInfo queryObject(String id) {
        return cardInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    @Override
    public List<CardInfo> queryList(Map<String, Object> map) {
        return cardInfoMapper.selectList(map);
    }

    /**
     * 保存
     *
     * @param cardInfo
     * @throws Exception
     */
    @Override
    public void save(CardInfo cardInfo) throws Exception {
        cardInfoMapper.insert(cardInfo);
    }

    /**
     * 更新
     *
     * @param cardInfo
     * @throws Exception
     */
    @Override
    public void update(CardInfo cardInfo) throws Exception {
        cardInfoMapper.updateByPrimaryKeySelective(cardInfo);
    }

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(String id) throws Exception {
        cardInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @throws Exception
     */
    @Override
    public void deleteBatch(String[] ids) throws Exception {
        cardInfoMapper.deleteBatch(ids);
    }
}
