package com.distribution.modules.sys.service

import com.distribution.modules.sys.entity.District

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.sys.service
 * @Description TODO(描述)
 * @create 2018/6/20-22:05
 */
interface DistrictService {
    fun getByParentId(parentId: String): List<District>
}
