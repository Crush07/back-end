package com.hysea.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : hysea
 * @Description :
 **/
@Configuration
public class TopicRabbitConfig {

    //绑定键
    public final static String FIRST = "topic.first";
    public final static String SECOND = "topic.second";

    //队列 起名：firstQueue
    @Bean
    public Queue firstQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("firstQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(FIRST,true);
    }

    //队列 起名：firstQueue
    @Bean
    public Queue secondQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("firstQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(SECOND,true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(FIRST);
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
    }

}