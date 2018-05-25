package com.distribution.modules.memeber.service;

import com.distribution.modules.memeber.entity.MemberAmountHistroy;

import java.util.List;
import java.util.Map;

public interface MemberAmountHistroyService {

    List<MemberAmountHistroy> findList(Map<String,Object> param);
}
