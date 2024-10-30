package com.hysea.core;

import lombok.Data;

@Data
public class Job {

    private int circle;

    private int interval;

    private Runnable runnable;

    private volatile boolean running;

}
