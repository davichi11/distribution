package com.distribution.queue

import org.springframework.amqp.core.AmqpTemplate
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
class LevelUpSender {
    @Autowired
    private val amqpTemplate: AmqpTemplate? = null

    fun send(context: String) {
        println("Sender : $context")
        amqpTemplate!!.convertAndSend("level_up", context)
    }
}
