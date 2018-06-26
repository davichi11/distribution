package com.distribution.modules.dis.service;

import com.distribution.modules.dis.entity.DisFans;

import java.util.List;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.dis.service
 * @Description TODO(描述)
 * @create 2018/5/11-23:04
 */
public interface DisFansService  {
    /**
     * 根据ID查询
     *
     * @return
     */
    DisFans queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<DisFans> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(DisFans disFans) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(DisFans disFans) throws Exception;

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
