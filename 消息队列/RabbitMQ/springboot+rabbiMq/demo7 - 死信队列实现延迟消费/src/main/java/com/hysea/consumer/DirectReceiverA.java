package com.hysea.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hysea.entity.Evaluation;
import com.hysea.event.EvaluationEvent;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class DirectReceiverA {

    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "direct.first", autoDelete = "false"),
//                    exchange = @Exchange(value = "directExchange", delayed = "true"),  rabbitmq-plugins enable rabbitmq_delayed_message_exchange 需要装这个插件
                    exchange = @Exchange(value = "directExchange"),
                    key = "directRouter"
            )
    )
    public void process(Message message, Channel channel) throws InterruptedException, IOException {

        //一次最多能处理多少条消息
        channel.basicQos(1);
        String evaluationMsg = new String(message.getBody());
        Evaluation evaluation = JSONObject.parseObject(evaluationMsg, Evaluation.class);
        System.out.println("插入评价！");
        System.out.println("评价：" + evaluation);
        System.out.println("评价更新-发送通知给家长");
        Thread.sleep(5000);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

    }

}