package com.hysea.producer;

import com.hysea.constant.TopicDestination;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * 队列消息生产者
 * <p>
 * 用于发送消息到指定的JMS队列
 */
@Component
public class QueueProducer {

    private final Queue queue;
    private final JmsMessagingTemplate jmsMessagingTemplate;

    public QueueProducer(Queue queue,JmsMessagingTemplate jmsMessagingTemplate) {
        this.queue = queue;
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    /**
     * 发送消息到指定的队列
     *
     * @param message 要发送的消息内容
     */
    public void send(String message) {
        // 调用 JmsMessagingTemplate 的 convertAndSend 方法，将消息发送到指定队列。
        jmsMessagingTemplate.convertAndSend(queue, message);
    }

}

