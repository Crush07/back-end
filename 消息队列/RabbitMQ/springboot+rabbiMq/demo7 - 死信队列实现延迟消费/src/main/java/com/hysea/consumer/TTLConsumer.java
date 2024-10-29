//package com.hysea.consumer;
//
//import com.hysea.entity.RabbitMessage;
//import com.hysea.event.CommonEvent;
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//@Component
//@RabbitListener(queues = "ttlQueue")
////public class TTLConsumer {
//public class TTLConsumer implements ApplicationListener<CommonEvent> {
//
//	public static Map<Long,RabbitMessage> messageMap = new HashMap<>();
//
//	@Autowired
//	ApplicationEventPublisher applicationEventPublisher;
//
//	@Override
//	public void onApplicationEvent(CommonEvent commonEvent) {
//
//		if(messageMap.size() % 2 == 0){
//			System.out.println("中途取消订单");
//			RabbitMessage message = (RabbitMessage) commonEvent.getData();
//			try {
//				message.getChannel().basicAck(message.getDeliveryTag(), false);
//
//				Set<Long> deliveryTagSet = messageMap.keySet();
//				for (Long deliveryTag : deliveryTagSet) {
//					messageMap.get(deliveryTag).getChannel().basicAck(deliveryTag, false);
//				}
//
//				messageMap.clear();
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
//
//	@RabbitHandler
//	public void process(Map<String, Object> message, Channel channel, Message mqMsg)
//			throws InterruptedException {
//		System.out.println("TTL收到消息" + message.toString());
//
//		long deliveryTag = mqMsg.getMessageProperties().getDeliveryTag();
//		String expiration = mqMsg.getMessageProperties().getExpiration();
////		mqMsg.getMessageProperties().setExpiration("10000");
//		System.out.println(expiration);
//
//		RabbitMessage rabbitMessage = new RabbitMessage();
//		rabbitMessage.setData(new String(mqMsg.getBody()));
//		rabbitMessage.setDeliveryTag(deliveryTag);
//		rabbitMessage.setChannel(channel);
//
//		messageMap.put(deliveryTag,rabbitMessage);
//
//		applicationEventPublisher.publishEvent(new CommonEvent<>(rabbitMessage));
//
//	}
//}