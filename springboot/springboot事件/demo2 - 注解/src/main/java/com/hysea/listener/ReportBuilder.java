package com.hysea.listener;

import com.hysea.entity.Evaluation;
import com.hysea.entity.User;
import com.hysea.event.CommonEvent;
import com.hysea.event.EvaluationEvent;
import com.hysea.event.UserEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ReportBuilder {

    @EventListener(EvaluationEvent.class)
    public void flushReport(EvaluationEvent event){
        Evaluation data = (Evaluation) event.getData();
        System.out.println("评价：" + data);
        System.out.println("评价更新-刷新报表");
    }

    @EventListener(UserEvent.class)
    public void flushReport(UserEvent event){
        User data = (User) event.getData();
        System.out.println("用户：" + data);
        System.out.println("用户更新-刷新报表");
    }

}
