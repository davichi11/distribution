package com.distribution;

import com.alibaba.fastjson.JSON;
import com.distribution.common.utils.OkHttpUtil;
import com.distribution.modules.card.entity.CardApiResponse;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution
 * @Description TODO(描述)
 * @create 2018/5/9-22:23
 */
public class MyTest {
    @Test
    public void test1() {
        Map<String, Object> map = new HashMap<>();
        map.put("level", 1);
        System.out.println(URLEncoder.encode(JSON.toJSONString(map)));
    }

    @Test
    public void test2() {
        //当前时间
        LocalDateTime start = LocalDateTime.now();
        //第二天开始时间
        LocalDateTime end = LocalDateTime.now().plusDays(1).with(LocalTime.MIN);

        Duration between = Duration.between(start, end);

        long hours = between.toHours();
        System.out.println(hours);
    }

    @Test
    public void test3() throws IOException {
        //先获取
        String url = OkHttpUtil.attachHttpGetParam("http://www.qichangkeji.vip/qckjgzhManager/DownSingleLoan/selectById.do", "id", "30");
        Request request = new Request.Builder().url(url).get().build();
        Response response = OkHttpUtil.execute(request);
        if (response.isSuccessful()) {
            CardApiResponse cardApiResponse = JSON.parseObject(response.body().string(), CardApiResponse.class);
            System.out.println(cardApiResponse);
        }
    }
}
