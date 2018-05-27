package com.distribution.modules.api.service.impl;

import com.distribution.common.exception.RRException;
import com.distribution.common.utils.CommonUtils;
import com.distribution.common.utils.DateUtils;
import com.distribution.modules.api.dao.UserDao;
import com.distribution.modules.api.entity.UserEntity;
import com.distribution.modules.api.service.UserService;
import com.distribution.modules.dis.dao.DisFansMapper;
import com.distribution.modules.dis.dao.DisMemberInfoDao;
import com.distribution.modules.dis.entity.DisFans;
import com.distribution.modules.dis.entity.DisMemberInfoEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private DisMemberInfoDao memberInfoDao;
    @Autowired
    private DisFansMapper fansMapper;

    @Override
    public UserEntity queryObject(String userId) {
        return userDao.queryObject(userId);
    }

    @Override
    public List<UserEntity> queryList(Map<String, Object> map) {
        return userDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return userDao.queryTotal(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String mobile, String password, String openId) throws Exception {
        UserEntity user = new UserEntity();
        user.setUserId(CommonUtils.getUUID());
        user.setMobile(mobile);
        user.setUsername(mobile);
        user.setPassword(DigestUtils.sha256Hex(password));
        user.setCreateTime(LocalDateTime.now());
        user.setUserId(CommonUtils.getUUID());
        userDao.save(user);
        //添加会员信息
        DisMemberInfoEntity member = new DisMemberInfoEntity();
        member.setId(CommonUtils.getUUID());
        member.setDisPlatformId("1");
        member.setUserEntity(user);
        //是否已是锁粉
        if (StringUtils.isNotBlank(openId)) {
            Map<String, Object> fansParam = new HashMap<>(2);
            fansParam.put("openId", openId);
            List<DisFans> disFansList = fansMapper.selectList(fansParam);
            if (CollectionUtils.isNotEmpty(disFansList)) {
                DisFans fans = disFansList.get(0);
                member.setParentId(fans.getDisMemberInfo().getParentId());
                member.setDisMemberParent(fans.getDisMemberInfo());
                member.setOpenId(fans.getWechatId());
            }
        }
        member.setDisLevel(0);
        member.setDisUserType("0");
        member.setAddTime(DateUtils.formatDateTime(LocalDateTime.now()));
        memberInfoDao.save(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserEntity user) throws Exception {
        userDao.update(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId) throws Exception {
        userDao.delete(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] userIds) throws Exception {
        userDao.deleteBatch(userIds);
    }

    @Override
    public UserEntity queryByMobile(String mobile) {
        return userDao.queryByMobile(mobile);
    }

    @Override
    public String login(String mobile, String password) {
        UserEntity user =
                Optional.ofNullable(queryByMobile(mobile)).filter(u -> u.getPassword().equals(DigestUtils.sha256Hex(password)))
                        .orElseThrow(() -> new RRException("手机号或密码错误"));
        return user.getUserId();
    }

}
