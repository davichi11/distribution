package com.distribution.modules.memeber.service;

import com.distribution.modules.memeber.entity.MemeberInfo;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author liuxinxin
 * @date  16:16
 * @param
 * @return
 */
public interface MemeberInfoService {
    /**
     * 根据ID查询
     *
     * @return
     */
    MemeberInfo queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<MemeberInfo> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(MemeberInfo memeberInfo) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(MemeberInfo memeberInfo) throws Exception;

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
