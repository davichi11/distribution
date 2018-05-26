package com.distribution.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信发送
 * @author ChunLiang Hu
 * @Company
 * @Project jsdemo
 * @Package com.gxzb.jsdemo.core.queue
 * @Description TODO(描述)
 * @create 2018/1/17-14:11
 */
@Component
public class NotifySender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String context) {
        rabbitTemplate.convertAndSend("notify", context);
    }
}
