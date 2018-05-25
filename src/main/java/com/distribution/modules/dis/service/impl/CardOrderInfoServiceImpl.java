package com.distribution.modules.dis.service.impl;

import com.distribution.modules.dis.dao.CardOrderInfoDao;
import com.distribution.modules.dis.entity.CardOrderInfoEntity;
import com.distribution.modules.dis.service.CardOrderInfoService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("cardOrderInfoService")
public class CardOrderInfoServiceImpl implements CardOrderInfoService {
    @Autowired
    private CardOrderInfoDao cardOrderInfoDao;

    @Override
    public CardOrderInfoEntity queryObject(String id) {
        return cardOrderInfoDao.queryObject(id);
    }

    @Override
    public List<CardOrderInfoEntity> queryList(Map<String, Object> map) {
        return cardOrderInfoDao.queryList(map);
    }


    @Override
    public void save(CardOrderInfoEntity CardOrderInfo) throws Exception {
        cardOrderInfoDao.save(CardOrderInfo);
    }

    @Override
    public void update(CardOrderInfoEntity CardOrderInfo) throws Exception {
        cardOrderInfoDao.update(CardOrderInfo);
    }

    @Override
    public void delete(String id) throws Exception {
        cardOrderInfoDao.delete(id);
    }

    @Override
    public void deleteBatch(String[] ids) throws Exception {
        cardOrderInfoDao.deleteBatch(ids);
    }

    /**
     * 根据查询条件统计订单数量
     *
     * @param param
     * @return
     */
    @Override
    public Integer countOrder(Map<String, Object> param) {
        if (MapUtils.isEmpty(param)) {
            return 0;
        }
        return cardOrderInfoDao.countOrder(param);
    }

}
