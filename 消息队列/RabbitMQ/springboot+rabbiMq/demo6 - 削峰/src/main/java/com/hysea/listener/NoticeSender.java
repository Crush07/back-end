package com.hysea.listener;

import com.hysea.entity.Evaluation;
import com.hysea.entity.User;
import com.hysea.event.CommonEvent;
import com.hysea.event.EvaluationEvent;
import com.hysea.event.UserEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NoticeSender {

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法


    @EventListener(EvaluationEvent.class)
    public void evaluateToSendNotice(EvaluationEvent event) {

        System.out.println(event.toString());
        rabbitTemplate.convertAndSend("directExchange","directRouter",event.toString());

    }

}
