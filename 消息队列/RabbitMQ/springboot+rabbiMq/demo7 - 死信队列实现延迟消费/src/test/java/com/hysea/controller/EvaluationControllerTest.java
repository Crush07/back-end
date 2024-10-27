package com.hysea.controller;


import com.hysea.entity.Evaluation;
import com.hysea.event.EvaluationEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
class EvaluationControllerTest {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Test
    void add() {

        for (int i = 0; i < 10; i++) {

            Evaluation evaluation = new Evaluation();
            evaluation.setEvaluator("hyy");
            evaluation.setData("data");

            applicationEventPublisher.publishEvent(new EvaluationEvent<>(evaluation));
        }

    }
}