package com.distribution;

import com.alibaba.fastjson.JSON;
import com.distribution.common.utils.OkHttpUtil;
import com.distribution.modules.card.entity.CardApiResponse;
import com.distribution.modules.dis.entity.DisFans;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    public void test5() {
//        System.out.println(new BigDecimal("568").setScale(2,BigDecimal.ROUND_HALF_EVEN).doubleValue());
        System.out.println(NumberUtils.toLong("15657122353"));
    }

    @Test
    public void test2() throws UnsupportedEncodingException {
        //当前时间
        LocalDateTime start = LocalDateTime.now();
        //第二天开始时间
        LocalDateTime end = LocalDateTime.now().plusDays(1).with(LocalTime.MIN);
        //计算时间差
        Duration between = Duration.between(start, end);
        System.out.println(between.toNanos());
    }

    @Test
    public void test3() throws IOException {
        //先获取
        String url = OkHttpUtil.INSTANCE.attachHttpGetParam("http://www.qichangkeji.vip/qckjgzhManager/DownSingleLoan/selectById.do", "id", "30");
        Request request = new Request.Builder().url(url).get().build();
        Response response = OkHttpUtil.INSTANCE.execute(request);
        if (response.isSuccessful()) {
            CardApiResponse cardApiResponse = JSON.parseObject(response.body().string(), CardApiResponse.class);
            System.out.println(cardApiResponse);
        }
    }
    @Test
    public void test4() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("name", "刘辉");
        params.put("phone", "18635136850");
        params.put("type", "1");
        params.put("goodsId", "18");
        params.put("idCard", "14052219860503301X");
        params.put("fatherId", "5710");
        params.put("otherUserId", "10904");
        String url = OkHttpUtil.INSTANCE.attachHttpGetParams("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do", params);
//        RequestBody formBody = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), JSON.toJSONString(params));
//        Request request = new Request.Builder().url("http://www.qichangkeji.vip/qckjgzhManager/DownUser/Add.do")
//                .post(formBody).build();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = OkHttpUtil.INSTANCE.execute(request)) {
            System.out.println(response.isSuccessful());
        }
    }
}
