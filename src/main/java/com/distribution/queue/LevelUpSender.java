package com.distribution.queue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project distribution
 * @Package com.distribution.queue
 * @Description TODO(描述)
 * @create 2018/5/12-10:31
 */
@Component
public class LevelUpSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String context) {
        System.out.println("Sender : " + context);
        amqpTemplate.convertAndSend("level_up", context);
    }
}
