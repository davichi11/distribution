package com.distribution.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project jsdemo
 * @Package com.gxzb.jsdemo.core.queue
 * @Description TODO(描述)
 * @create 2018/1/17-14:13
 */
@Slf4j
@Component
@RabbitListener(queues = "notify")
public class NotifyReceiver {
    @SuppressWarnings("unchecked")
    @RabbitHandler
    public void process(String msg) {
        System.out.println("消费队列消息:" + msg);
    }
}
