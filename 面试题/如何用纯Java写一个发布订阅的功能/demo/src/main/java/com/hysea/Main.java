package com.hysea;

import com.hysea.listener.impl.EvaluationListenerImpl;
import com.hysea.publisher.EvaluationPublisher;

public class Main {
    public static void main(String[] args) {

        EvaluationPublisher evaluationPublisher = new EvaluationPublisher();
        evaluationPublisher.setListener(new EvaluationListenerImpl());
        evaluationPublisher.evaluation("vary good");

    }
}