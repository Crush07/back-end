package com.hysea.entity;

import com.rabbitmq.client.Channel;
import lombok.Data;

@Data
public class RabbitMessage {

    long deliveryTag;

    String data;

    Channel channel;

}
