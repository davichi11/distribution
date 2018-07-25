package com.distribution

import com.alibaba.fastjson.JSON
import com.distribution.modules.api.pojo.vo.LoanOrderVO
import com.distribution.modules.sys.service.SysUserService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.io.IOException

@RunWith(SpringRunner::class)
@SpringBootTest
class DistributionApplicationTests {
    @Autowired
    internal lateinit var userService: SysUserService
    @Autowired
    internal lateinit var context: WebApplicationContext
    @Autowired
    internal lateinit var redisTemplate: RedisTemplate<String, Any>

    private lateinit var mockMvc: MockMvc

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    fun contextLoads() {
        userService.queryList(mapOf()).forEach { println(it) }

    }

    @Test
    @Throws(IOException::class)
    fun testIdCard() {
        println(redisTemplate.opsForValue().increment("worker_id", 1))
    }

    @Test
    @Throws(Exception::class)
    fun testAop() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/testDrools"))
                .andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Throws(Exception::class)
    fun testLoan() {
        val orderVO = LoanOrderVO()
        orderVO.orderMobile = "18635136850"
        orderVO.orderIdcardno = "14052219860503301X"
        orderVO.loanId = "18"
        mockMvc.perform(MockMvcRequestBuilders.post("/api/loan")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0ZDU2N2FjNjYwYzc0MWFlYTY1MzYzNjE3ZTcxMTk5YyIsImlhdCI6MTUzMTE4MjExMSwiZXhwIjoxNTMxODE1NzExfQ.p6kx0XTxXIj1I8DQHJ3Cr2Jy8ggy9DHUVxi1Vabz_nI")
                .content(JSON.toJSONString(orderVO)))
                .andExpect(MockMvcResultMatchers.status().isOk).andDo(MockMvcResultHandlers.print())
    }

}
