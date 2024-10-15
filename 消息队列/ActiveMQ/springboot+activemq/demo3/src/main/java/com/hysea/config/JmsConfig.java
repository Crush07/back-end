package com.hysea.config;

import com.hysea.constant.QueueDestination;
import com.hysea.constant.TopicDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class JmsConfig {

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(QueueDestination.TEST_QUEUE_2);
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic(TopicDestination.TEST_TOPIC_2);
    }

}

