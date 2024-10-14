package com.hysea.consumer;

import com.hysea.constant.QueueDestination;
import com.hysea.constant.TopicDestination;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    @JmsListener(destination = QueueDestination.TEST_QUEUE_1, containerFactory = "jmsListenerContainerQueue")
    private void receive1(String message) {
        System.out.println("队列消费者2，接收消息: " + message);
    }

    @JmsListener(destination = QueueDestination.TEST_QUEUE_1, containerFactory = "jmsListenerContainerQueue")
    private void receive2(String message) {
        System.out.println("队列消费者2，接收消息: " + message);
    }

}

