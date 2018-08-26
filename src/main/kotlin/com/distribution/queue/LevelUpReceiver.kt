package com.distribution.queue

import com.alibaba.fastjson.JSON
import com.distribution.modules.dis.service.DisMemberInfoService
import org.apache.commons.lang.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.queue
 * @Description TODO(描述)
 * @create 2018/5/12-10:31
 */
@Component
@RabbitListener(queues = ["level_up"])
class LevelUpReceiver {
    private val log: Logger = LoggerFactory.getLogger(LevelUpReceiver::class.java)
    @Autowired
    private lateinit var disMemberInfoService: DisMemberInfoService

    @RabbitHandler
    fun process(msg: String) {
        log.info("进入会员升级队列,数据为{}", msg)
        if (StringUtils.isBlank(msg)) {
            log.error("消息为空")
            return
        }
        try {
            val memberObject = JSON.parseObject(msg)
            val memberInfo = disMemberInfoService.queryObject(memberObject.getString("id"))
            memberInfo?.let { disMemberInfoService.levelUp(it) }
        } catch (e: Exception) {
            log.error("会员升级异常", e)
        }
    }

}
