package com.hysea.consumer;

import com.hysea.entity.RabbitMessage;
import com.hysea.event.CommonEvent;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RabbitListener(queues = "ttlQueue")
public class TTLConsumer {
//	public class TTLConsumer implements ApplicationListener<CommonEvent> {


//	public static List<RabbitMessage> messageList = new ArrayList<>();

//	@Autowired
//	ApplicationEventPublisher applicationEventPublisher;
//
//	@Override
//	public void onApplicationEvent(CommonEvent commonEvent) {
//
//		if(messageList.size() % 2 == 0){
//			System.out.println("中途取消订单");
//			RabbitMessage message = (RabbitMessage) commonEvent.getData();
//			try {
//				message.getChannel().basicAck(message.getDeliveryTag(), false);
//
//				for (RabbitMessage rabbitMessage : messageList) {
//					rabbitMessage.getChannel().basicAck(rabbitMessage.getDeliveryTag(), false);
//				}
//
//				messageList.clear();
//
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//
//
//		}else{
//			System.out.println("创建订单");
//		}
//	}

	@RabbitHandler
	public void process(Map<String, Object> message, Channel channel, Message mqMsg)
			throws IOException {
		System.out.println("TTL收到消息" + message.toString());
		channel.basicReject(mqMsg.getMessageProperties().getDeliveryTag(), false);

//		RabbitMessage rabbitMessage = new RabbitMessage();
//		rabbitMessage.setData(new String(mqMsg.getBody()));
//		rabbitMessage.setDeliveryTag(mqMsg.getMessageProperties().getDeliveryTag());
//		rabbitMessage.setChannel(channel);
//
//		messageList.add(rabbitMessage);
//
//		applicationEventPublisher.publishEvent(new CommonEvent<>(rabbitMessage));
	}
}