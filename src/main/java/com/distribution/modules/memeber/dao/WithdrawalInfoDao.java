package com.distribution.modules.memeber.dao;

import com.distribution.modules.memeber.entity.WithdrawalInfo;
import com.distribution.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WithdrawalInfoDao extends BaseDao<WithdrawalInfo> {

<<<<<<< HEAD
    List<WithdrawalInfo> findAmounts(String withdrawMobile);
=======
    List<WithdrawalInfo> findByAliPayAccount(String withdrawMobile);
>>>>>>> origin/develop
}
