package com.distribution.modules.api.service;


import com.distribution.modules.api.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:06
 */
public interface UserService {
    /**
     * 根据用户ID查询用户数据
     *
     * @param userId
     * @return
     */
    UserEntity queryObject(String userId);

    /**
     * 查询用户列表
     *
     * @param map
     * @return
     */
    List<UserEntity> queryList(Map<String, Object> map);

    /**
     * 查询总记录数
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 根据手机号和密码保存用户
     *
     * @param mobile
     * @param password
     * @param openId 微信openID
     * @throws Exception
     */
    void save(String mobile, String password,String openId) throws Exception;

    /**
     * 更新
     *
     * @param user
     * @throws Exception
     */
    void update(UserEntity user) throws Exception;

    /**
     * 删除
     *
     * @param userId
     * @throws Exception
     */
    void delete(Long userId) throws Exception;

    /**
     * 批量删除
     *
     * @param userIds
     * @throws Exception
     */
    void deleteBatch(Long[] userIds) throws Exception;

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    UserEntity queryByMobile(String mobile);

    /**
     * 用户登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @return 返回用户ID
     */
    String login(String mobile, String password);


}
