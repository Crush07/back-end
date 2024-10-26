package com.hysea.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redis")
public class RedisController {

    private final RedisTemplate redisTemplate;

    public RedisController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * curl -i -X GET "http://localhost:8081/redis/save?key=hello&value=redis"
     * @param key
     * @param value
     * @return
     */
    @GetMapping("save")
    public String save(String key, String value){
        redisTemplate.opsForValue().set(key, value);
        return "ok";
    }

}
