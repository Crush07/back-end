package com.hysea.consumer;
 
import java.io.IOException;
import java.util.Map;
 
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
import com.rabbitmq.client.Channel;
 
@Component
@RabbitListener(queues = "normalQueue")
public class NormalConsumer {
	
	@RabbitHandler
	public void process(Map<String, Object> message, Channel channel, Message mqMsg) 
			throws IOException {
		System.out.println("收到消息，并拒绝重新入队:" + message.toString());
		channel.basicReject(mqMsg.getMessageProperties().getDeliveryTag(), false);
	}
	
}