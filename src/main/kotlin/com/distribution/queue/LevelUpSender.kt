package com.distribution.queue

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.support.CorrelationData
import org.springframework.beans.factory.InitializingBean
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
class LevelUpSender : RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback, InitializingBean {
    private val log: Logger = LoggerFactory.getLogger(LevelUpSender::class.java)
    @Autowired
    private lateinit var rabbitAmqpTemplate: RabbitTemplate
    /**
     * Returned message callback.
     * @param message the returned message.
     * @param replyCode the reply code.
     * @param replyText the reply text.
     * @param exchange the exchange.
     * @param routingKey the routing key.
     */
    override fun returnedMessage(message: Message?, replyCode: Int, replyText: String?, exchange: String?, routingKey: String?) {
        log.info("发送房源数据到B端,数据为:$message")
    }

    /**
     * Confirmation callback.
     * @param correlationData correlation data for the callback.
     * @param ack true for ack, false for nack
     * @param cause An optional cause, for nack, when available, otherwise null.
     */
    override fun confirm(correlationData: CorrelationData?, ack: Boolean, cause: String?) {
        if (ack) {
            log.info("消息发送成功")
        } else {
            log.error("消息发送失败")
        }
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     *
     * This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     * @throws Exception in the event of misconfiguration (such
     * as failure to set an essential property) or if initialization fails.
     */
    override fun afterPropertiesSet() {
        rabbitAmqpTemplate.setConfirmCallback(this)
        rabbitAmqpTemplate.setReturnCallback(this)
    }

    fun send(context: String) {
        println("Sender : $context")
        rabbitAmqpTemplate.convertAndSend("level_up", context)
    }
}
