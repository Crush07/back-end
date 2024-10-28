package com.hysea.controller;
 
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
 
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class TestController {
 
	@Autowired
	private RabbitTemplate rabbitTemplate;
 
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
		Map<String, Object> map = new HashMap<>();
		map.put("messageId", String.valueOf(UUID.randomUUID()));
		map.put("data", System.currentTimeMillis() + ", ttl队列消息");
		rabbitTemplate.convertAndSend("normalExchange", "ttlRouting", map, new CorrelationData());
		return "success";
	}
}