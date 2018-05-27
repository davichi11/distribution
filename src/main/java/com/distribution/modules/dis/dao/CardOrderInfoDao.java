package com.distribution.modules.dis.dao;

import com.distribution.modules.dis.entity.CardOrderInfoEntity;
import com.distribution.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 办卡订单信息表
 *
 * @author huchunliang
 * @email davichi2009@gmail.com
 * @date 2018-05-08
 */
@Mapper
public interface CardOrderInfoDao extends BaseDao<CardOrderInfoEntity> {
    /**
     * 根据查询条件查询订单数量
     * @param param
     * @return
     */
    Integer countOrder(Map<String, Object> param);

    /**
     * 批量修改订单状态
     * @param orderStatus
     * @param ids
     */
    void statusUpdate(Map<String,Object> map);

    List<CardOrderInfoEntity> queryListByIds(List ids);

}
