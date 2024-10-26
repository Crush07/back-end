package com.hysea.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hysea.entity.Element;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leaderboard")
public class RedisController {

    private final RedisTemplate<String,Object> redisTemplate;

    public RedisController(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * curl -i -X GET "http://localhost:8081/leaderboard"
     * @return
     */
    @GetMapping()
    public String getLeaderboard(){
        List<Element> elementList = redisTemplate.opsForZSet().rangeWithScores("list", 0, -1).stream().map(s -> {
            Element element = new Element();
            element.setId((Long) s.getValue());
            element.setScore(s.getScore());
            return element;
        }).collect(Collectors.toList());
        return elementList.toString();
    }

    /**
     * curl -i -X PUT --header "Content-Type:application/json" "http://localhost:8081/leaderboard" -d "{\"id\":1,\"score\":8}"
     * @return
     */
    @PutMapping()
    public String addElement(@RequestBody Element element){
        Boolean list = redisTemplate.opsForZSet().add("list", element.getId(), element.getScore());
        return "ok";
    }

}
