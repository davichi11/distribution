package com.distribution.modules.memeber.service.impl;

import com.distribution.modules.memeber.dao.WithdrawalInfoDao;
import com.distribution.modules.memeber.entity.WithdrawalInfo;
import com.distribution.modules.memeber.service.WithdrawalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Double aDouble=0.00;
        Map<String, Object> map = new HashMap<>();
        map.put("withdrawMobile", withdrawMobile);
        List<WithdrawalInfo> withdrawalInfoList = withdrawalInfoDao.queryList(map);
        for (WithdrawalInfo withdrawalInfo : withdrawalInfoList) {
            aDouble=withdrawalInfo.getWithdrawAmount().add(new BigDecimal(aDouble)).doubleValue();
        }
        return BigDecimal.valueOf(aDouble);
    }

    /**
     * 提现记录查询
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/22 22:32
     * @Description:
     */
    public List<WithdrawalInfo> queryList(String withdrawMobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("withdrawMobile", withdrawMobile);
        return withdrawalInfoDao.queryList(map);
    }

    /**
     *  添加提现信息
     * @Auther: liuxinxin
     * @Date: 2018/5/22 22:50
     * @Description:
     */
    public void save(WithdrawalInfo withdrawalInfo) throws Exception {
        withdrawalInfoDao.save(withdrawalInfo);
    }
}
