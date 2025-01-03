package com.hysea.listener;

import com.hysea.entity.Evaluation;
import com.hysea.entity.User;
import com.hysea.event.CommonEvent;
import com.hysea.event.EvaluationEvent;
import com.hysea.event.UserEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NoticeSender implements ApplicationListener<EvaluationEvent> {

    @Override
    public void onApplicationEvent(EvaluationEvent event) {

        Evaluation data = (Evaluation) event.getData();
        System.out.println("评价：" + data);
        System.out.println("评价更新-发送通知给家长");

    }

}
