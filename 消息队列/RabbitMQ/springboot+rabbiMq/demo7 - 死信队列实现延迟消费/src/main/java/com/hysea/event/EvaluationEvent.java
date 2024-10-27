package com.hysea.event;

import org.springframework.context.ApplicationEvent;

public class EvaluationEvent<T> extends CommonEvent{

    public EvaluationEvent(Object source) {
        super(source);
    }

}
