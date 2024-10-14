package com.hysea.controller;

import com.hysea.producer.QueueProducer;
import com.hysea.producer.TopicProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : hysea
 * @Description :
 **/
@RestController
public class SendMessageController {

    @Autowired
    private TopicProducer topicProducer;

    @Autowired
    private QueueProducer queueProducer;

    /**
     * curl -i http://localhost:8081/sendToQueue
     * @return
     */
    @GetMapping("/sendToQueue")
    public String sendToQueue() {
        for (int i = 0; i < 10; i++) {
            queueProducer.send("Hello ActiveMQ, queue message" + i);
        }
        return "ok";
    }

    /**
     * curl -i http://localhost:8081/sendToTopic
     * @return
     */
    @GetMapping("/sendToTopic")
    public String sendToTopic() {
        for (int i = 0; i < 10; i++) {
            topicProducer.send("Hello ActiveMQ, topic message" + i);
        }
        return "ok";
    }

}