package com.distribution;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.net.URLEncoder;
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
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        System.out.println(path);
    }
}
