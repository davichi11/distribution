package com.distribution.modules.dis.service.impl;

import com.distribution.modules.api.dao.UserDao;
import com.distribution.modules.api.entity.UserEntity;
import com.distribution.modules.dis.dao.CardOrderInfoDao;
import com.distribution.modules.dis.dao.DisFansMapper;
import com.distribution.modules.dis.dao.DisMemberInfoDao;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import com.distribution.modules.dis.service.DisMemberInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service()
public class DisMemberInfoServiceImpl implements DisMemberInfoService {
    @Autowired
    private DisMemberInfoDao disMemberInfoDao;
    @Autowired
    private CardOrderInfoDao cardOrderInfoDao;
    @Autowired
    private DisFansMapper disFansMapper;
    @Autowired
    private UserDao userDao;

    @Override
    public DisMemberInfoEntity queryObject(String id) {
        return disMemberInfoDao.queryObject(id);
    }

    @Override
    public DisMemberInfoEntity queryByOpenId(String openId) {
        return disMemberInfoDao.findByOpenId(openId);
    }

    @Override
    public DisMemberInfoEntity queryByMobile(String mobile) {
        return disMemberInfoDao.queryByMobile(mobile);
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
    public void updateDisLevel(Integer level, String type, String id) throws Exception {
        disMemberInfoDao.updateDisLevel(level, type, id);
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
    @Transactional(rollbackFor = Exception.class)
    public boolean levelUp(DisMemberInfoEntity memberInfo) throws Exception {
        //如果是非会员升级,查询其下线完成的订单和锁粉信息,大于三个并且锁粉达到10个可以升级为三级会员
        if ("0".equals(memberInfo.getDisUserType())) {
            //查询锁粉数据
            Map<String, Object> param = new HashMap<>(2);
            param.put("memberId", memberInfo.getId());
            List<DisFans> disFansList = disFansMapper.selectList(param);

            if (disFansList.size() >= 10) {
                Map<String, Object> memberParam = new HashMap<>();
                memberParam.put("openIds", disFansList.stream().map(DisFans::getWechatId).collect(Collectors.toList()));
                List<String> fansMemberIds = disMemberInfoDao.queryList(memberParam).stream().map(DisMemberInfoEntity::getId)
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(fansMemberIds)) {
                    memberParam.put("memberIds", fansMemberIds);
                }
                long count = cardOrderInfoDao.queryList(memberParam).stream()
                        .filter(order -> order.getOrderStatus().equals(1)).count();
                //
                if (count >= 3) {
                    memberInfo.setDisUserType("1");
                    memberInfo.setDisLevel(3);
                    UserEntity userEntity = userDao.queryByMemberId(memberInfo.getId());
                    memberInfo.setUserEntity(userEntity);
                    update(memberInfo);
                    return parentLevelUp(memberInfo.getDisMemberParent());
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
    private boolean parentLevelUp(DisMemberInfoEntity member) throws Exception {
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
            disMemberInfoDao.updateDisLevel(member.getDisLevel(), member.getDisUserType(), member.getId());

        }
        //二级升一级
        if (member.getDisLevel() == 2 && children >= 15) {
            member.setDisLevel(1);
            disMemberInfoDao.updateDisLevel(member.getDisLevel(), member.getDisUserType(), member.getId());
        }
        return parentLevelUp(parent);
    }


}
