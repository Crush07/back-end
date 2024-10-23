package com.hysea.controller;

import com.hysea.entity.User;
import com.hysea.handle.KafkaSendResultHandler;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
 
/**
 * @author 徐一杰
 * @date 2022/10/31 14:05
 * kafka发送消息
 */
@RestController
@RequestMapping("/provider")
//这个注解代表这个类开启Springboot事务，因为我们在Kafka的配置文件开启了Kafka事务，不然会报错
@Transactional(rollbackFor = RuntimeException.class)
public class KafkaController {
 
    private final KafkaTemplate<Object, Object> kafkaTemplate;
 
    public KafkaController(KafkaTemplate<Object, Object> kafkaTemplate, KafkaSendResultHandler kafkaSendResultHandler) {
        this.kafkaTemplate = kafkaTemplate;
        //回调方法、异常处理
        this.kafkaTemplate.setProducerListener(kafkaSendResultHandler);
    }
 
    @RequestMapping("/sendMultiple")
    public void sendMultiple() {
        String message = "发送到Kafka的消息";
        for (int i = 0;i < 10;i++) {
            kafkaTemplate.send("topic1", "发送到Kafka的消息" + i);
            System.out.println(message + i);
        }
    }
 
    @RequestMapping("/send")
    public void send() {
        //这个User的代码我没放出来，自己随便写一个实体类，实体类一定要 implements Serializable
        User user = new User(1, "徐一杰");
        kafkaTemplate.send("topic1", user);
        kafkaTemplate.send("topic2", user);
    }
 
    /**
     * Kafka提供了多种构建消息的方式
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public void SendDemo() throws ExecutionException, InterruptedException, TimeoutException {
        //后面的get代表同步发送，括号内时间可选，代表超过这个时间会抛出超时异常，但是仍会发送成功
        kafkaTemplate.send("topic1", "发给topic1").get(1, TimeUnit.MILLISECONDS);
 
        //使用ProducerRecord发送消息
        ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>("topic.quick.demo", "use ProducerRecord to send message");
        kafkaTemplate.send(producerRecord);
 
        //使用Message发送消息
        Map<String, Object> map = new HashMap<>();
        map.put(KafkaHeaders.TOPIC, "topic.quick.demo");
        map.put(KafkaHeaders.PARTITION_ID, 0);
        map.put(KafkaHeaders.MESSAGE_KEY, 0);
        GenericMessage<Object> message = new GenericMessage<>("use Message to send message", new MessageHeaders(map));
        kafkaTemplate.send(message);
    }
}