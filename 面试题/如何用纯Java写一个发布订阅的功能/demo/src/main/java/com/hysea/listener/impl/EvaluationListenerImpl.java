package com.hysea.listener.impl;

import com.hysea.listener.EvaluationListener;

import java.util.logging.Logger;

public class EvaluationListenerImpl implements EvaluationListener {

    private static final Logger logger = Logger.getLogger(EvaluationListenerImpl.class.getName());

    @Override
    public void evaluate(String data) {
        logger.info("监听到评价" + data);
    }
}
