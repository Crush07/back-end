package com.hysea.listener;

import java.util.EventListener;

//还可以这么写
//public class EvaluationListener implements EventListener {
public interface EvaluationListener extends EventListener {

    public void evaluate(String data);

}
