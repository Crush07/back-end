package com.hysea.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : hysea
 * @Description :
 **/
@Configuration
public class FanoutRabbitConfig {

    //队列 起名：fanout.a
    @Bean
    public Queue queueA() {
        return new Queue("fanout.a");
    }

    //队列 起名：fanout.b
    @Bean
    public Queue queueB() {
        return new Queue("fanout.b");
    }

    //队列 起名：fanout.c
    @Bean
    public Queue queueC() {
        return new Queue("fanout.c");
    }

    //队列 起名：direct.a
    @Bean
    public Queue queueDirectA() {
        return new Queue("direct.a");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeMessage3() {
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeMessage4() {
        return BindingBuilder.bind(queueDirectA()).to(directExchange()).with("directRouter");
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);

        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("ConfirmCallback:     " + "相关数据：" + correlationData);
            System.out.println("ConfirmCallback:     " + "确认情况：" + ack);
            System.out.println("ConfirmCallback:     " + "原因：" + cause);
        });



        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("ReturnCallback:     "+"消息："+message);
            System.out.println("ReturnCallback:     "+"回应码："+replyCode);
            System.out.println("ReturnCallback:     "+"回应信息："+replyText);
            System.out.println("ReturnCallback:     "+"交换机："+exchange);
            System.out.println("ReturnCallback:     "+"路由键："+routingKey);
        });

        return rabbitTemplate;
    }

}