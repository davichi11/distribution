package com.distribution;

import com.distribution.modules.dis.dao.DisFansMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution
 * @create 2019-02-20-20:50
 */
public class TestJava extends DistributionApplicationTests {
    @Autowired
    private DisFansMapper fansMapper;

    @Test
    public void test1() {
        fansMapper.selectByPrimaryKey("123");
    }
}
