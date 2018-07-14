package com.distribution.modules.dis.service;

import com.distribution.modules.dis.entity.CardOrderInfoEntity;

import java.util.List;
import java.util.Map;


/**
 * 办卡订单信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */
public interface CardOrderInfoService {
    /**
     * 根据ID查询
     *
     * @return
     */
    CardOrderInfoEntity queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<CardOrderInfoEntity> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(CardOrderInfoEntity cardOrderInfo) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(CardOrderInfoEntity cardOrderInfo) throws Exception;

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
     * 根据查询条件统计订单数量
     *
     * @param param
     * @return
     */
    Integer countOrder(Map<String, Object> param);

    /**
     * 统计用户办理的某张卡片的数量
     * @param memberId
     * @param banNum
     * @return
     */
    Integer countUserCard(String memberId, String banNum);

    /**
     * 批量修改订单状态
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/27 15:22
     * @Description:
     */
    void statusUpdate(Map<String, Object> map) throws Exception;
}
