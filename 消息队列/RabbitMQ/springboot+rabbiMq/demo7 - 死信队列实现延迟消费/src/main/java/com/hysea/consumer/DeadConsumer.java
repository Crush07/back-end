package com.hysea.consumer;
 
import java.io.IOException;
import java.util.Map;
 
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
import com.rabbitmq.client.Channel;
 
@Component
@RabbitListener(queues = "deadQueue")
public class DeadConsumer {
	
	@RabbitHandler
	public void process(Map<String, Object> message, Channel channel, Message mqMsg) 
			throws IOException {
		System.out.println("订单自动取消:" + message.toString());
		channel.basicAck(mqMsg.getMessageProperties().getDeliveryTag(), false);
	}
}