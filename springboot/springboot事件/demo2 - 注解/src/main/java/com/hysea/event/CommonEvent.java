package com.hysea.event;
import org.springframework.context.ApplicationEvent;

public class CommonEvent<T> extends ApplicationEvent {

    T data;

    public CommonEvent(T source) {
        super(source);
        this.data = source;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}



