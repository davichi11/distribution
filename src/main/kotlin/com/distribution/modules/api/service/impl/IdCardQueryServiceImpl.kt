package com.distribution.modules.api.service.impl

import com.alibaba.fastjson.JSON
import com.distribution.common.utils.OkHttpUtil
import com.distribution.modules.api.entity.IdCardResponse
import com.distribution.modules.api.service.IdCardQueryService
import okhttp3.Request
import org.springframework.stereotype.Service
import java.io.IOException

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.service.impl
 
 * @create 2018/6/9-13:17
 */
@Service
class IdCardQueryServiceImpl : IdCardQueryService {
    @Throws(IOException::class)
    override fun isMatched(idCard: String, name: String): Boolean {
        val appKey = "aa3c7957d52abc30bf5b3b0f80fea3d5"
        val map = mapOf("idcard" to idCard, "realname" to name, "key" to appKey)
        val url = OkHttpUtil.attachHttpGetParams("http://op.juhe.cn/idcard/query", map)
        val request = Request.Builder().url(url).get().build()
        val response = OkHttpUtil.execute(request)
        if (!response.isSuccessful) {
            return false
        }
        val query = JSON.parseObject(response.body()!!.string(), IdCardResponse::class.java)
        return query.errorCode == 0 && query.result.res == 1
    }
}
