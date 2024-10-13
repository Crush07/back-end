package com.hysea.consumer;

import com.hysea.constant.QueueDestination;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    @JmsListener(destination = QueueDestination.TEST_QUEUE_1)
    private void receive(String message) {
        System.out.println("队列消费者，接收消息: " + message);
    }

}

