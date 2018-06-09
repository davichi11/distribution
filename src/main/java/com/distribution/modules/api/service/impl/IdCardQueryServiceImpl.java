package com.distribution.modules.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.distribution.common.utils.OkHttpUtil;
import com.distribution.modules.api.entity.IdCardResponse;
import com.distribution.modules.api.service.IdCardQueryService;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.modules.api.service.impl
 * @Description TODO(描述)
 * @create 2018/6/9-13:17
 */
@Service
public class IdCardQueryServiceImpl implements IdCardQueryService {
    @Override
    public boolean isMatched(String idCard, String name) throws IOException {
        String appKey = "aa3c7957d52abc30bf5b3b0f80fea3d5";
        Map<String, String> map = new HashMap<>();
        map.put("idcard", idCard);
        map.put("realname", name);
        map.put("key", appKey);
        String url = OkHttpUtil.attachHttpGetParams("http://op.juhe.cn/idcard/query", map);
        Request request = new Request.Builder().url(url).get().build();
        Response response = OkHttpUtil.execute(request);
        if (!response.isSuccessful()) {
            return false;
        }
        IdCardResponse query = JSON.parseObject(response.body().string(), IdCardResponse.class);
        return query.getErrorCode() == 0 && query.getResult().getRes() == 1;
    }
}
