package com.hysea.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiverB {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("DirectReceiver消费者B收到消息  : " + testMessage.toString());
    }

}