package com.distribution.modules.sys.service

import com.alicp.jetcache.anno.CacheType
import com.alicp.jetcache.anno.Cached
import com.distribution.modules.sys.entity.District
import java.util.concurrent.TimeUnit

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.sys.service
 * @Description TODO(描述)
 * @create 2018/6/20-22:05
 */
interface DistrictService {
    @Cached(name = "DistrictService.", key = "#parentId", cacheType = CacheType.BOTH, localExpire = 600000, expire = 6000000, timeUnit = TimeUnit.SECONDS)
    fun getByParentId(parentId: String): List<District>
}
