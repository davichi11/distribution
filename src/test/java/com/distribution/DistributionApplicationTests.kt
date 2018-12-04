package com.distribution

import com.alibaba.fastjson.JSON
import com.distribution.modules.api.pojo.vo.LoanOrderVO
import com.distribution.modules.api.service.IdCardQueryService
import com.distribution.modules.dis.service.DisMemberInfoService
import com.distribution.modules.sys.service.SysConfigService
import com.distribution.modules.sys.service.SysUserService
import com.distribution.queue.LevelUpSender
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
    lateinit var userService: SysUserService
    @Autowired
    lateinit var context: WebApplicationContext
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>
    @Autowired
    lateinit var idCardQueryService: IdCardQueryService
    @Autowired
    lateinit var disMemberInfoService: DisMemberInfoService
    @Autowired
    lateinit var sysConfigService: SysConfigService


    @Autowired
    lateinit var levelUpSender: LevelUpSender


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

    @Test
    fun testIdCardQuery() {
        print("------------------------------------")
        print(idCardQueryService.isMatched("140109198701040532", "胡春亮"))
        print("------------------------------------")
    }


    @Test
    fun testCacheConfig() {
        //{"level_1":"568","level_2":"368","level_3":"168"}
        val value = "123"
        val key = "level_price"
        sysConfigService.updateValueByKey(key, value)
    }

    @Test
    fun testMyTeam() {
        //查询所有锁粉信息
        val myTeam = disMemberInfoService.findMyTeam("9f71511fad6d4e2d8b1f854e084a9d71")
        val disFansList = myTeam.filter { "0" == it.disUserType || it.disUserType == null }

        //所有代理信息
        val children = myTeam.filter { "1" == it.disUserType }

        //返回数据
        val map = mapOf("countFans" to disFansList.size, "fansList" to disFansList,
                "countChirldern" to children.size, "children" to children)

        print(map)
    }

}
