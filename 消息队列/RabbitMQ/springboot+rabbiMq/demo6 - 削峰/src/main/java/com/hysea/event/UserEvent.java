package com.hysea.event;

import org.springframework.context.ApplicationEvent;

public class UserEvent<T> extends CommonEvent{

    public UserEvent(Object source) {
        super(source);
    }

}
