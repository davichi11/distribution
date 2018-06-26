package com.distribution;

import com.distribution.common.utils.CommonUtils;
import org.junit.Test;

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
        System.out.println(CommonUtils.getRandom());
    }

    @Test
    public void test2() {
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        System.out.println(path);
    }
}
