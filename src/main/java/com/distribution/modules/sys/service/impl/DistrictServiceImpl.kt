package com.distribution.modules.sys.service.impl

import com.distribution.modules.sys.dao.DistrictDao
import com.distribution.modules.sys.entity.District
import com.distribution.modules.sys.service.DistrictService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.sys.service.impl
 * @Description TODO(描述)
 * @create 2018/6/20-22:05
 */
@Service
class DistrictServiceImpl : DistrictService {
    @Autowired
    private val districtDao: DistrictDao? = null

    override fun getByParentId(parentId: String): List<District> {
        return districtDao!!.getByParentId(parentId)
    }
}
