package com.distribution.modules.api.dao;

import com.distribution.modules.api.entity.UserEntity;
import com.distribution.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-23 15:22:06
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    /**
     * 根据手机号查询
     *
     * @param mobile
     * @return
     */
    UserEntity queryByMobile(String mobile);

    /**
     * 根据会员ID查询用户信息
     * @param memberId
     * @return
     */
    UserEntity queryByMemberId(String memberId);

}
