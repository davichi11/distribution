package com.distribution.modules.dis.service.impl;

import com.distribution.modules.dis.dao.CardOrderInfoDao;
import com.distribution.modules.dis.dao.DisFansMapper;
import com.distribution.modules.dis.dao.DisMemberInfoDao;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.DisMemberInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service()
public class DisMemberInfoServiceImpl implements DisMemberInfoService {
    @Autowired
    private DisMemberInfoDao disMemberInfoDao;
    @Autowired
    private CardOrderInfoDao cardOrderInfoDao;
    @Autowired
    private DisFansMapper disFansMapper;

    @Override
    public DisMemberInfoEntity queryObject(String id) {
        return disMemberInfoDao.queryObject(id);
    }

    @Override
    public List<DisMemberInfoEntity> queryList(Map<String, Object> map) {
        return disMemberInfoDao.queryList(map);
    }

    @Override
    public void save(DisMemberInfoEntity disMemberInfo) throws Exception {
        disMemberInfoDao.save(disMemberInfo);
    }

    @Override
    public void update(DisMemberInfoEntity disMemberInfo) throws Exception {
        disMemberInfoDao.update(disMemberInfo);
    }

    @Override
    public void delete(String id) throws Exception {
        disMemberInfoDao.delete(id);
    }

    @Override
    public void deleteBatch(String[] ids) throws Exception {
        disMemberInfoDao.deleteBatch(ids);
    }

    @Override
    public boolean levelUp(DisMemberInfoEntity memberInfo) {
        //如果是非会员升级,查询其完成的订单和锁粉信息,大于三个并且锁粉达到10个可以升级为三级会员
        if ("0".equals(memberInfo.getDisUserType())) {
            //查询锁粉数据
            Map<String, Object> param = new HashMap<>(2);
            param.put("memberId", memberInfo.getId());
            List<DisFans> disFansList = disFansMapper.selectList(param);

            Map<String, Object> orderParam = new HashMap<>(2);
            orderParam.put("member_id", memberInfo.getId());
            orderParam.put("orderStatus", "1");
            Integer count = cardOrderInfoDao.countOrder(orderParam);
            if (count >= 3 && disFansList.size() >= 10) {
                memberInfo.setDisUserType("1");
                memberInfo.setDisLevel(3);
                try {
                    update(memberInfo);
                    return parentLevelUp(memberInfo.getDisMemberParent());
                } catch (Exception e) {
                    log.error(memberInfo.getDisUserName() + "升级异常", e);
                    return false;
                }
            }
        }

        return false;
    }

    /**
     * 递归升级会员
     *
     * @param member
     * @return
     */
    private boolean parentLevelUp(DisMemberInfoEntity member) {
        DisMemberInfoEntity parent = disMemberInfoDao.queryObject(member.getId()).getDisMemberParent();
        if (member.getDisLevel() == 1 || parent == null) {
            return true;
        }
        //查询会员的下级会员数量
        Map<String, Object> param = new HashMap<>(2);
        param.put("parent_id", member.getId());
        long children = disMemberInfoDao.queryList(param).stream().filter(m -> "1".equals(m.getDisUserType())).count();
        //三级升二级
        if (member.getDisLevel() == 3 && children >= 5) {
            member.setDisLevel(2);
            try {
                disMemberInfoDao.update(member);
            } catch (Exception e) {
                log.error(String.format("会员%s升级异常", member.getDisUserName()), e);
                return false;
            }
        }
        //二级升一级
        if (member.getDisLevel() == 2 && children >= 15) {
            member.setDisLevel(1);
            try {
                disMemberInfoDao.update(member);
            } catch (Exception e) {
                log.error(String.format("会员%s升级异常", member.getDisUserName()), e);
                return false;
            }
        }
        return parentLevelUp(parent);
    }


}
