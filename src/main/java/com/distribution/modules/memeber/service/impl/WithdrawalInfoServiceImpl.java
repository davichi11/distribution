package com.distribution.modules.memeber.service.impl;

import com.distribution.modules.memeber.dao.WithdrawalInfoDao;
import com.distribution.modules.memeber.entity.WithdrawalInfo;
import com.distribution.modules.memeber.service.WithdrawalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
import org.springframework.transaction.annotation.Transactional;
>>>>>>> origin/develop

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
<<<<<<< HEAD
        Double aDouble=0.00;
        Map<String, Object> map = new HashMap<>();
        map.put("withdrawMobile", withdrawMobile);
        List<WithdrawalInfo> withdrawalInfoList = withdrawalInfoDao.queryList(map);
        for (WithdrawalInfo withdrawalInfo : withdrawalInfoList) {
            aDouble=withdrawalInfo.getWithdrawAmount().add(new BigDecimal(aDouble)).doubleValue();
        }
=======
        Map<String, Object> map = new HashMap<>(2);
        map.put("withdrawMobile", withdrawMobile);
        List<WithdrawalInfo> withdrawalInfoList = withdrawalInfoDao.queryList(map);
        Double aDouble = withdrawalInfoList.stream().mapToDouble(value -> value.getWithdrawAmount().doubleValue()).sum();
>>>>>>> origin/develop
        return BigDecimal.valueOf(aDouble);
    }

    /**
     * 提现记录查询
     *
     * @Auther: liuxinxin
     * @Date: 2018/5/22 22:32
     * @Description:
     */
<<<<<<< HEAD
    public List<WithdrawalInfo> queryList(String withdrawMobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("withdrawMobile", withdrawMobile);
        return withdrawalInfoDao.queryList(map);
    }

    /**
     *  添加提现信息
=======
    @Override
    public List<WithdrawalInfo> queryList(Map<String, Object> params) {
        return withdrawalInfoDao.queryList(params);
    }

    /**
     * 添加提现信息
     *
>>>>>>> origin/develop
     * @Auther: liuxinxin
     * @Date: 2018/5/22 22:50
     * @Description:
     */
<<<<<<< HEAD
    public void save(WithdrawalInfo withdrawalInfo) throws Exception {
        withdrawalInfoDao.save(withdrawalInfo);
    }
=======
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(WithdrawalInfo withdrawalInfo) throws Exception {
        withdrawalInfoDao.save(withdrawalInfo);
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Override
    public WithdrawalInfo querySingle(String id) {
        return withdrawalInfoDao.queryObject(id);
    }
>>>>>>> origin/develop
}
