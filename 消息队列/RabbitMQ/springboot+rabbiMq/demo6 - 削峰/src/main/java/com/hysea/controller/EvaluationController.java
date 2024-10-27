package com.hysea.controller;

import com.hysea.entity.Evaluation;
import com.hysea.event.EvaluationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @eo.api-type http
 * @eo.groupName 默认分组
 * @eo.path /evaluate
 */

@RestController
@RequestMapping("/evaluate")
public class EvaluationController {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;


    /**
     * curl -X POST http://localhost:8081/evaluate/add -H "Content-Type: application/json" -d "{\"evaluator\":\"hyy\",\"data\":\"A\"}"
     * @param evaluation
     * @return
     */
    @PostMapping("/add")
    public String add(@RequestBody Evaluation evaluation){

        System.out.println("插入评价！");

        applicationEventPublisher.publishEvent(new EvaluationEvent<>(evaluation));

        return "评价成功";
    }

}
