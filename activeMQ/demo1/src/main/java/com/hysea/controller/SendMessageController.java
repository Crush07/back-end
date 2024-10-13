package com.hysea.controller;

import com.hysea.producer.QueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author : hysea
 * @Description :
 **/
@RestController
public class SendMessageController {

    @Autowired
    private QueueProducer producer;

    /**
     * 发送消息给 扇形交换机
     * curl -i http://localhost:8081/send
     * @return
     */
    @GetMapping("/send")
    public String send() {
        producer.send("Hello ActiveMQ, queue message");
        return "ok";
    }

}