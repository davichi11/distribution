package com.distribution.config

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author huchunliang
 */
@Configuration
class QueueConfig {

    /**
     * 创建DLX exchange
     *
     * @return
     */
    @Bean
     fun delayExchange(): DirectExchange {
        return DirectExchange(DELAY_EXCHANGE_NAME)
    }

    /**
     * 创建per_queue_ttl_exchange
     *
     * @return
     */
    @Bean
     fun perQueueTTLExchange(): DirectExchange {
        return DirectExchange(PER_QUEUE_TTL_EXCHANGE_NAME)
    }

    /**
     * 短信发送队列
     *
     * @return
     */
    @Bean
     fun notifyQueue(): Queue {
        return QueueBuilder.durable("notify").build()
    }

    /**
     * 会员自动升级队列
     *
     * @return
     */
    @Bean
     fun levelUpQueue(): Queue {
        return QueueBuilder.durable("level_up").build()
    }

    /**
     * 创建delay_queue_per_message_ttl队列
     *
     * @return
     */
    @Bean
     fun delayQueuePerMessageTTL(): Queue {
        return QueueBuilder.durable(DELAY_QUEUE_PER_MESSAGE_TTL_NAME)
                // DLX，dead letter发送到的exchange
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE_NAME)
                // dead letter携带的routing key
                .withArgument("x-dead-letter-routing-key", DELAY_PROCESS_QUEUE_NAME)
                .build()
    }

    /**
     * 创建delay_queue_per_queue_ttl队列
     *
     * @return
     */
    @Bean
     fun delayQueuePerQueueTTL(): Queue {
        return QueueBuilder.durable(DELAY_QUEUE_PER_QUEUE_TTL_NAME)
                // DLX
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE_NAME)
                // dead letter携带的routing key
                .withArgument("x-dead-letter-routing-key", DELAY_PROCESS_QUEUE_NAME)
                // 设置队列的过期时间
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .build()
    }

    /**
     * 创建delay_process_queue队列，也就是实际消费队列
     *
     * @return
     */
    @Bean
     fun delayProcessQueue(): Queue {
        return QueueBuilder.durable(DELAY_PROCESS_QUEUE_NAME)
                .build()
    }

    /**
     * 将DLX绑定到实际消费队列
     *
     * @param delayProcessQueue
     * @param delayExchange
     * @return
     */
    @Bean
     fun dlxBinding(delayProcessQueue: Queue, delayExchange: DirectExchange): Binding {
        return BindingBuilder.bind(delayProcessQueue)
                .to(delayExchange)
                .with(DELAY_PROCESS_QUEUE_NAME)
    }

    /**
     * 将per_queue_ttl_exchange绑定到delay_queue_per_queue_ttl队列
     *
     * @param delayQueuePerQueueTTL
     * @param perQueueTTLExchange
     * @return
     */
    @Bean
     fun queueTTLBinding(delayQueuePerQueueTTL: Queue, perQueueTTLExchange: DirectExchange): Binding {
        return BindingBuilder.bind(delayQueuePerQueueTTL)
                .to(perQueueTTLExchange)
                .with(DELAY_QUEUE_PER_QUEUE_TTL_NAME)
    }

    /**
     * 定义delay_process_queue队列的Listener Container
     *
     * @param connectionFactory
     * @return
     */
    @Bean
     fun processContainer(connectionFactory: ConnectionFactory, processReceiver: ProcessReceiver): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        // 监听delay_process_queue
        container.setQueueNames(DELAY_PROCESS_QUEUE_NAME)
        container.setMessageListener(MessageListenerAdapter(processReceiver))
        return container
    }

    companion object {

        /**
         * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
         * 每个message可以控制自己的失效时间
         */
        private val DELAY_QUEUE_PER_MESSAGE_TTL_NAME = "delay_queue_per_message_ttl"

        /**
         * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
         * 队列里所有的message都有统一的失效时间
         */
         val DELAY_QUEUE_PER_QUEUE_TTL_NAME = "delay_queue_per_queue_ttl"
        private val QUEUE_EXPIRATION = 60000

        /**
         * message失效后进入的队列，也就是实际的消费队列
         */
        private val DELAY_PROCESS_QUEUE_NAME = "delay_process_queue"

        /**
         * DLX
         */
        private val DELAY_EXCHANGE_NAME = "delay_exchange"

        /**
         * 路由到delay_queue_per_queue_ttl的exchange
         */
        val PER_QUEUE_TTL_EXCHANGE_NAME = "per_queue_ttl_exchange"
    }
}
