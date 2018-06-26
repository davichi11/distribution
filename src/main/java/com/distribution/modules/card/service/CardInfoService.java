package com.distribution.modules.card.service;

import com.distribution.modules.card.entity.CardInfo;

import java.util.List;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.card.service
 * @Description TODO(描述)
 * @create 2018/5/8-20:21
 */
public interface CardInfoService {

    /**
     * 根据ID查询
     *
     * @return
     */
    CardInfo queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<CardInfo> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(CardInfo cardInfo) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(CardInfo cardInfo) throws Exception;

    /**
     * 删除
     *
     * @throws Exception
     */
    void delete(String id) throws Exception;

    /**
     * 批量删除
     *
     * @throws Exception
     */
    void deleteBatch(String[] ids) throws Exception;
}
