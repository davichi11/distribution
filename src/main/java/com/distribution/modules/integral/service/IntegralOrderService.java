package com.distribution.modules.integral.service;

import com.distribution.modules.integral.entity.IntegralOrderEntity;

import java.util.List;
import java.util.Map;


/**
 * 积分兑换申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
public interface IntegralOrderService {
    /**
     * 根据ID查询
     *
     * @return
     */
    IntegralOrderEntity queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<IntegralOrderEntity> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(IntegralOrderEntity integralOrder) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(IntegralOrderEntity integralOrder,Double money) throws Exception;

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
