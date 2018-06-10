package com.distribution.modules.dis.service.impl;

import com.distribution.modules.dis.dao.CardOrderInfoDao;
import com.distribution.modules.dis.dao.DisMemberInfoDao;
import com.distribution.modules.dis.entity.CardOrderInfoEntity;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.CardOrderInfoService;
import com.distribution.modules.dis.service.DisProfiParamService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("cardOrderInfoService")
public class CardOrderInfoServiceImpl implements CardOrderInfoService {
    @Autowired
    private CardOrderInfoDao cardOrderInfoDao;

    @Autowired
    private DisProfiParamService disProfiParamService;
    @Autowired
    private DisMemberInfoDao disMemberInfoDao;

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

    /**
     * 批量修改订单状态
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/27 15:20
     * @Description:
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void statusUpdate(Map<String, Object> map) throws Exception {
        cardOrderInfoDao.statusUpdate(map);
        int status = (int) map.get("orderStatus");
        //订单成功后调用分润
        if (1 == status) {
            List<CardOrderInfoEntity> cardOrderInfoEntityList = cardOrderInfoDao.queryListByIds((List) map.get("ids"));
            for (CardOrderInfoEntity cardOrderInfoEntity : cardOrderInfoEntityList) {
                //非会员不分润
                if ("0".equals(cardOrderInfoEntity.getMemberInfo().getDisUserType())) {
                    continue;
                }
                DisMemberInfoEntity member = disMemberInfoDao.queryObject(cardOrderInfoEntity.getMemberInfo().getId());
                //调用分润
                disProfiParamService.doFeeSplitting(member, cardOrderInfoEntity.getCardInfo().getRebate(), false);
            }
        }
    }
}
