package com.distribution.modules.pos.service;

import com.distribution.modules.pos.entity.PosApplyEntity;

import java.util.List;
import java.util.Map;


/**
 * POS机申请表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-06-20
 */
public interface PosApplyService {
    /**
     * 根据ID查询
     *
     * @return
     */
    PosApplyEntity queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<PosApplyEntity> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(PosApplyEntity PosApply) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(PosApplyEntity PosApply) throws Exception;

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
