package com.distribution.modules.integral.service;

import com.distribution.modules.integral.entity.ProductDetailEntity;

import java.util.List;
import java.util.Map;


/**
 * 积分兑换产品列表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-07-01
 */
public interface ProductDetailService {
    /**
     * 根据ID查询
     *
     * @return
     */
    ProductDetailEntity queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<ProductDetailEntity> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(ProductDetailEntity productDetail) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(ProductDetailEntity productDetail) throws Exception;

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
