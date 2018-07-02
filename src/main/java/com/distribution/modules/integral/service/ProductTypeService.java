package com.distribution.modules.integral.service;

import com.distribution.modules.integral.entity.ProductTypeEntity;

import java.util.List;
import java.util.Map;


/**
 * 积分兑换产品类型表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
public interface ProductTypeService {
    /**
     * 根据ID查询
     *
     * @return
     */
    ProductTypeEntity queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<ProductTypeEntity> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(ProductTypeEntity productType) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(ProductTypeEntity productType) throws Exception;

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
