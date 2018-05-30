package com.distribution;

import org.junit.Test;

import java.math.BigDecimal;

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
        System.out.println(new BigDecimal(5000).compareTo(new BigDecimal(50000)));
    }

    @Test
    public void test2() {
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        System.out.println(path);
    }
}
