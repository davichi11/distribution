package com.distribution.modules.sys.service.impl;

import com.distribution.modules.sys.dao.DistrictDao;
import com.distribution.modules.sys.entity.District;
import com.distribution.modules.sys.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.sys.service.impl
 * @Description TODO(描述)
 * @create 2018/6/20-22:05
 */
@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictDao districtDao;
    @Override
    public List<District> getByParentId(String parentId) {
        return districtDao.getByParentId(parentId);
    }
}
