package com.hysea.producer;

import com.hysea.constant.QueueDestination;
import com.hysea.constant.TopicDestination;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * 队列消息生产者
 * <p>
 * 用于发送消息到指定的JMS队列
 */
@Component
public class TopicProducer {

    /**
     * JmsMessagingTemplate 用于消息的发送，它封装了消息转换和发送的逻辑
     */
    private final JmsMessagingTemplate jmsMessagingTemplate;

    public TopicProducer(JmsMessagingTemplate jmsMessagingTemplate) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    /**
     * 发送消息到指定的队列
     *
     * @param message 要发送的消息内容
     */
    public void send(String message) {
        // 调用 JmsMessagingTemplate 的 convertAndSend 方法，将消息发送到指定队列。
        jmsMessagingTemplate.convertAndSend(TopicDestination.TEST_TOPIC_1, message);
    }

}

