package com.hysea.publisher;

import com.hysea.listener.EvaluationListener;

import java.util.List;

public class EvaluationPublisher {

    private EvaluationListener listener;

    public void setListener(EvaluationListener listener) {
        this.listener = listener;
    }

    public void evaluation(String data){
        listener.evaluate(data);
    }

}
