package com.hysea.controller;

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
    private TopicProducer producer;

    /**
     * curl -i http://localhost:8081/send
     * @return
     */
    @GetMapping("/send")
    public String send() {
        producer.send("Hello ActiveMQ, topic message");
        return "ok";
    }

}