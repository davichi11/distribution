package com.distribution.modules.memeber.service.impl;

import com.distribution.modules.memeber.dao.MemberAmountHistroyDao;
import com.distribution.modules.memeber.entity.MemberAmountHistroy;
import com.distribution.modules.memeber.service.MemberAmountHistroyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class MemberAmountHistroyServiceImpl implements MemberAmountHistroyService {

    @Autowired
    private MemberAmountHistroyDao memberAmountHistroyDao;

    @Override
    public List<MemberAmountHistroy> findList(Map<String,Object> param) {
        return memberAmountHistroyDao.queryList(param);
    }
}
