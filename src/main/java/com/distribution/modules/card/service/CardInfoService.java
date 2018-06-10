package com.distribution.modules.card.service;

import com.distribution.modules.card.entity.CardApiResponse;
import com.distribution.modules.card.entity.CardInfo;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;

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

    CardInfo queryByBankNum(String bankNum);

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

    /**
     * 获取信用卡信息
     * @param prodId
     * @return
     * @throws Exception
     */
    CardApiResponse getProductInfo(String prodId) throws Exception;

    /**
     * 提交用户信息并返回办卡链接,失败返回空
     * @param member
     * @param prodId
     * @return
     * @throws Exception
     */
    String getProductUrl(DisMemberInfoEntity member, String prodId) throws Exception;
}
