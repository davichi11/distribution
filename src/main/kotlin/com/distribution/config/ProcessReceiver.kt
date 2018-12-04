package com.distribution.config

import com.rabbitmq.client.Channel
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class ProcessReceiver : ChannelAwareMessageListener {

    @Throws(Exception::class)
    override fun onMessage(message: Message, channel: Channel) {
        try {
            processMessage(message)
        } catch (e: Exception) {
            // 如果发生了异常，则将该消息重定向到缓冲队列，会在一定延迟之后自动重做
            channel.basicPublish(QueueConfig.PER_QUEUE_TTL_EXCHANGE_NAME, QueueConfig.DELAY_QUEUE_PER_QUEUE_TTL_NAME, null,
                    "The failed message will auto retry after a certain delay".toByteArray())
        }

        if (latch != null) {
            latch!!.countDown()
        }
    }

    /**
     * 模拟消息处理。如果当消息内容为FAIL_MESSAGE的话，则需要抛出异常
     *
     * @param message
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun processMessage(message: Message) {
        val realMessage = String(message.body)
        logger.info("Received: < $realMessage >")
        if (realMessage == FAIL_MESSAGE) {
            throw Exception("Some exception happened")
        }
    }

    companion object {
        var latch: CountDownLatch? = null
        private val logger = LoggerFactory.getLogger(ProcessReceiver::class.java)

        val FAIL_MESSAGE = "This message will fail"
    }
}
