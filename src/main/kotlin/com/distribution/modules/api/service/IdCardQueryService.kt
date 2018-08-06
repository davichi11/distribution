package com.distribution.modules.api.service

import java.io.IOException

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.service
 * @Description TODO(描述)
 * @create 2018/6/9-13:15
 */
interface IdCardQueryService {
    /**
     * 身份证实名认证
     * @param idCard
     * @param name
     * @return
     */
    @Throws(IOException::class)
    fun isMatched(idCard: String, name: String): Boolean
}
