package com.hysea.handle;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
 
/**
 * @author 徐一杰
 * @date 2022/10/31 15:41
 * kafka消息发送回调
 */
@Component
public class KafkaSendResultHandler implements ProducerListener<Object, Object> {
 
    @Override
    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        System.out.println("消息发送成功：" + producerRecord.toString());
    }

    @Override
    public void onError(ProducerRecord<Object, Object> producerRecord, Exception exception) {
        System.out.println("消息发送失败：" + producerRecord.toString() + exception.getMessage());

    }

}