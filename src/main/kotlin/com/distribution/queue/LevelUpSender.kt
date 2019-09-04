package com.distribution.queue

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.queue
 
 * @create 2018/5/12-10:31
 */
@Component
class LevelUpSender {
    private val log: Logger = LoggerFactory.getLogger(LevelUpSender::class.java)
    @Autowired
    private lateinit var rabbitAmqpTemplate: RabbitTemplate

    fun send(context: String) {
        println("Sender : $context")
        rabbitAmqpTemplate.convertAndSend("level_up", context)
    }
}
