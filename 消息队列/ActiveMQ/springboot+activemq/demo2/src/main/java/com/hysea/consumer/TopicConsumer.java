package com.hysea.consumer;

import com.hysea.constant.QueueDestination;
import com.hysea.constant.TopicDestination;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    @JmsListener(destination = TopicDestination.TEST_TOPIC_1, containerFactory = "jmsListenerContainerTopic")
    private void receive1(String message) {
        System.out.println("主题消费者1，接收消息: " + message);
    }

    @JmsListener(destination = TopicDestination.TEST_TOPIC_1, containerFactory = "jmsListenerContainerTopic")
    private void receive2(String message) {
        System.out.println("主题消费者2，接收消息: " + message);
    }

    @JmsListener(destination = TopicDestination.TEST_TOPIC_1, containerFactory = "jmsListenerContainerTopic")
    private void receive3(String message) {
        System.out.println("主题消费者3，接收消息: " + message);
    }

}

