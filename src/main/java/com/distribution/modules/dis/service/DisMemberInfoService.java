package com.distribution.modules.dis.service;

import com.distribution.modules.dis.entity.DisMemberInfoEntity;

import java.util.List;
import java.util.Map;


/**
 * 用户表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-03
 */
public interface DisMemberInfoService {
    /**
     * 根据ID查询
     *
     * @return
     */
    DisMemberInfoEntity queryObject(String id);

    /**
     * 根据openID查询会员信息
     *
     * @param openId
     * @return
     */
    DisMemberInfoEntity queryByOpenId(String openId);

    /**
     * 根据手机号查询
     *
     * @param mobile
     * @return
     */
    DisMemberInfoEntity queryByMobile(String mobile);

    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<DisMemberInfoEntity> queryList(Map<String, Object> map);

    /**
     * 保存
     *
     * @throws Exception
     */
    void save(DisMemberInfoEntity disMemberInfo) throws Exception;

    /**
     * 更新
     *
     * @throws Exception
     */
    void update(DisMemberInfoEntity disMemberInfo) throws Exception;

    void updateDisLevel(Integer level, String type, String id) throws Exception;

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
     * 会员升级逻辑
     *
     * @param memberInfo
     * @return
     */
    boolean levelUp(DisMemberInfoEntity memberInfo) throws Exception;

}
