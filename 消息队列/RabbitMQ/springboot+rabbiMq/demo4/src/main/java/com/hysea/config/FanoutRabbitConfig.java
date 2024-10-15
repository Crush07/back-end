package com.hysea.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : hysea
 * @Description :
 **/
@Configuration
public class FanoutRabbitConfig {

    //队列 起名：queueA
    @Bean
    public Queue queueA() {
        return new Queue("fanout.a");
    }

    //队列 起名：queueB
    @Bean
    public Queue queueB() {
        return new Queue("fanout.b");
    }

    //队列 起名：queueC
    @Bean
    public Queue queueC() {
        return new Queue("fanout.c");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
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

}