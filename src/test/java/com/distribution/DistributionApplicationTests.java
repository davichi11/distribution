package com.distribution;

import com.alibaba.fastjson.JSON;
import com.distribution.modules.api.pojo.vo.LoanOrderVO;
import com.distribution.modules.api.service.IdCardQueryService;
import com.distribution.modules.sys.service.SysUserService;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributionApplicationTests {
    @Autowired
    SysUserService userService;
    @Autowired
    IdCardQueryService idCardQueryService;
    @Autowired
    WebApplicationContext context;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void contextLoads() {
        userService.queryList(Maps.newHashMap()).forEach(System.out::println);

    }

    @Test
    public void testIdCard() throws IOException {
        System.out.println(redisTemplate.opsForValue().increment("worker_id", 1));
    }


    public long getIncrValue(final String key) {

        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer=redisTemplate.getStringSerializer();
            byte[] rowkey=serializer.serialize(key);
            byte[] rowval=connection.get(rowkey);
            try {
                String val=serializer.deserialize(rowval);
                return Long.parseLong(val);
            } catch (Exception e) {
                return 0L;
            }
        });
    }

    @Test
    public void testAop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/testDrools"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testLoan() throws Exception {
        LoanOrderVO orderVO = new LoanOrderVO();
        orderVO.setOrderMobile("18635136850");
        orderVO.setOrderIdcardno("14052219860503301X");
        orderVO.setLoanId("18");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/loan")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0ZDU2N2FjNjYwYzc0MWFlYTY1MzYzNjE3ZTcxMTk5YyIsImlhdCI6MTUzMTE4MjExMSwiZXhwIjoxNTMxODE1NzExfQ.p6kx0XTxXIj1I8DQHJ3Cr2Jy8ggy9DHUVxi1Vabz_nI")
                .content(JSON.toJSONString(orderVO)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

}
