package com.hysea.controller;
 
import java.util.*;

import com.hysea.entity.RabbitMessage;
import com.hysea.event.CommonEvent;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class TestController implements ApplicationListener<CommonEvent> {
 
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	public static List<RabbitMessage> messageList = new ArrayList<>();

	@Override
	public void onApplicationEvent(CommonEvent commonEvent) {

		if(messageList.size() % 2 == 0){
			System.out.println("中途取消订单");
			messageList.clear();
		}else{
			System.out.println("创建订单");
		}
	}
 
	/**
	 * curl -i -X GET "http://localhost:8081/normalQueue"
	 * 正常消息队列，队列最大长度5
	 */
	@GetMapping("/normalQueue")
	public String normalQueue() {
		Map<String, Object> map = new HashMap<>();
		map.put("messageId", String.valueOf(UUID.randomUUID()));
		map.put("data", System.currentTimeMillis() + ", 正常队列消息，最大长度5");
		rabbitTemplate.convertAndSend("normalExchange", "normalRouting", map, new CorrelationData());
		return "success";
	}

	/**
	 * curl -i -X GET "http://localhost:8081/ttlToDead"
	 * 消息 TTL, time to live
	 */
	@GetMapping("/ttlToDead")
	public String ttlToDead() {
		String data = System.currentTimeMillis() + ", ttl队列消息";
		Map<String, Object> map = new HashMap<>();
		map.put("messageId", String.valueOf(UUID.randomUUID()));
		map.put("data", data);
		rabbitTemplate.convertAndSend("normalExchange", "ttlRouting", map, message -> {
			message.getMessageProperties().setExpiration("10000");
			return message;
		});

		RabbitMessage rabbitMessage = new RabbitMessage();

		messageList.add(rabbitMessage);

		applicationEventPublisher.publishEvent(new CommonEvent<>(rabbitMessage));
		return "success";
	}
}