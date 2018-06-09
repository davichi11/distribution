package com.distribution;

import com.distribution.modules.api.service.IdCardQueryService;
import com.distribution.modules.sys.service.SysUserService;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        boolean matched = idCardQueryService.isMatched("140109198701040532", "胡春亮");
        System.out.println(matched);
    }

    @Test
    public void testAop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/testDrools"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

}
