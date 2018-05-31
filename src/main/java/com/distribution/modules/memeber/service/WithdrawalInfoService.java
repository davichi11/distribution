package com.distribution.modules.memeber.service;

import com.distribution.modules.memeber.entity.WithdrawalInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WithdrawalInfoService {

    /**
     * 根据用户手机号查询用户提现金额
     *
     * @param withdrawMobile
     * @return
     */
    BigDecimal withdrawalAmounts(String withdrawMobile);

    /**
     * 提现记录查询
     *
     * @param params
     * @return
     */
    List<WithdrawalInfo> queryList(Map<String, Object> params);

    /**
     * 添加提现信息
     *
     * @param withdrawalInfo
     * @throws Exception
     */
    void save(WithdrawalInfo withdrawalInfo) throws Exception;

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    WithdrawalInfo querySingle(String id);
}
