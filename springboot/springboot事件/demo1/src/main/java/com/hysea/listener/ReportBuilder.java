package com.hysea.listener;

import com.hysea.entity.Evaluation;
import com.hysea.entity.User;
import com.hysea.event.CommonEvent;
import com.hysea.event.EvaluationEvent;
import com.hysea.event.UserEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ReportBuilder implements ApplicationListener<CommonEvent> {

    @Override
    public void onApplicationEvent(CommonEvent event) {
        if(event instanceof EvaluationEvent){

            Evaluation data = (Evaluation) event.getData();
            System.out.println("评价：" + data);
            System.out.println("评价更新-刷新报表");

        } else if (event instanceof UserEvent) {

            User data = (User) event.getData();
            System.out.println("用户：" + data);
            System.out.println("用户更新-刷新报表");

        }
    }

}
