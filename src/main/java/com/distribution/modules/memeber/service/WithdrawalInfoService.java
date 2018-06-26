package com.distribution.modules.memeber.service;

import com.distribution.modules.memeber.entity.WithdrawalInfo;

import java.math.BigDecimal;
import java.util.List;

public interface WithdrawalInfoService {


    BigDecimal withdrawalAmounts(String withdrawMobile);

    List<WithdrawalInfo> queryList(String withdrawMobile);

    void save(WithdrawalInfo withdrawalInfo)throws Exception;
}
