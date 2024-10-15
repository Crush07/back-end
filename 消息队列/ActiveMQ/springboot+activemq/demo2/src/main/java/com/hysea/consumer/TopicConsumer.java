package com.hysea.consumer;

import com.hysea.constant.QueueDestination;
import com.hysea.constant.TopicDestination;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    @JmsListener(destination = TopicDestination.TEST_TOPIC_1)
    private void receive(String message) {
        System.out.println("队列消费者，接收消息: " + message);
    }

}

