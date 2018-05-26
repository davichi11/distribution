package com.distribution.modules.dis.service;

import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.entity.DisProfiParam;

import java.util.List;
import java.util.Map;


/**
 * 分润参数设置
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-22
 */
public interface DisProfiParamService {
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    DisProfiParam queryObject(String id);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<DisProfiParam> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @param disProfiParam
     * @throws Exception
     */
    void save(DisProfiParam disProfiParam) throws Exception;

    /**
     * 更新
     *
     * @param disProfiParam
     * @throws Exception
     */
    void update(DisProfiParam disProfiParam) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    void delete(String id) throws Exception;

    /**
     * 批量删除
     *
     * @param ids
     * @throws Exception
     */
    void deleteBatch(String[] ids) throws Exception;

    /**
     * 分润逻辑
     *
     * @param member     会员信息
     * @param money      金额
     * @param isReward 是否是会员推荐奖励
     */
    void doFeeSplitting(DisMemberInfoEntity member, Double money,boolean isReward) throws Exception;
}
