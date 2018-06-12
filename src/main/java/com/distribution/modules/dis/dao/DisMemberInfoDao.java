package com.distribution.modules.dis.dao;

import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;

/**
 * 用户表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-03
 */
@Mapper
public interface DisMemberInfoDao extends BaseDao<DisMemberInfoEntity> {
    /**
     * 根据open的ID查询
     * @param openId
     * @return
     */
    DisMemberInfoEntity findByOpenId(String openId);

    DisMemberInfoEntity queryByMobile(String mobile);

    void updateDisLevel(@Param("level") Integer level, @Param("type")String type, @Param("id") String id);
}
