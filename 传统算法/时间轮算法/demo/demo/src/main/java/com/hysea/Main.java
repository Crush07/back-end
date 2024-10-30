package com.hysea;

import com.hysea.core.TimeWheel;

public class Main {
    public static void main(String[] args) throws Exception {

        TimeWheel timeWheel = new TimeWheel(60);

        timeWheel.addJob(()->{
            System.out.print("第十秒  ");
            System.out.println(System.currentTimeMillis());
        },0,10);
        timeWheel.addJob(()->{
            System.out.print("第十秒  ");
            System.out.println(System.currentTimeMillis());
        },0,10);
        timeWheel.addJob(()->{
            System.out.print("第十秒  ");
            System.out.println(System.currentTimeMillis());
        },0,10);
        timeWheel.addJob(()->{
            System.out.print("第十秒  ");
            System.out.println(System.currentTimeMillis());
        },0,10);
        timeWheel.addJob(()->{
            System.out.print("第十秒  ");
            System.out.println(System.currentTimeMillis());
        },0,10);

        timeWheel.start();

    }
}