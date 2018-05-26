package com.distribution.modules.memeber.service.impl;

import com.distribution.modules.memeber.dao.WithdrawalInfoDao;
import com.distribution.modules.memeber.entity.WithdrawalInfo;
import com.distribution.modules.memeber.service.WithdrawalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WithdrawalInfoServiceImpl implements WithdrawalInfoService {

    @Autowired
    private WithdrawalInfoDao withdrawalInfoDao;

    /**
     * 根据用户手机号查询用户提现金额
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/22 22:30
     * @Description:
     */
    @Override
    public BigDecimal withdrawalAmounts(String withdrawMobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("withdrawMobile", withdrawMobile);
        List<WithdrawalInfo> withdrawalInfoList = withdrawalInfoDao.queryList(map);
        Double aDouble = withdrawalInfoList.stream().mapToDouble(value -> value.getWithdrawAmount().doubleValue()).sum();
        return BigDecimal.valueOf(aDouble);
    }

    /**
     * 提现记录查询
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/22 22:32
     * @Description:
     */
    @Override
    public List<WithdrawalInfo> queryList(String withdrawMobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("withdrawMobile", withdrawMobile);
        return withdrawalInfoDao.queryList(map);
    }

    /**
     * 添加提现信息
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/22 22:50
     * @Description:
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(WithdrawalInfo withdrawalInfo) throws Exception {
        withdrawalInfoDao.save(withdrawalInfo);
    }
}
