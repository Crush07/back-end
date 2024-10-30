package com.hysea.core;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Data
public class TimeWheel {

    /**
     * 时间轮执行任务的最小时间单位
     */
    private final static int duration = 1000;

    /**
     * 时间轮指针
     */
    private volatile int current = 0;

    /**
     * 控制时间轮转动的线程：每隔duration ms 时间轮指针就往前一格
     */
    private Thread tick;

    /**
     * 任务队列
     */
    private List<Job>[] slots;

    /**
     * 一圈的任务队列数量
     */
    private int slotCount;

    /**
     * 当前圈数
     */
    private int circle;

    /**
     * 执行任务的线程池
     */
    private ThreadPoolExecutor executor;

    /**
     * 时间轮开始启动的时间戳
     */
    private long startTime;

    /**
     * 时间轮开始启动
     */
    public void start(){
        this.tick.start();
        this.startTime = System.currentTimeMillis();
    }
    private void tick(){

        List<Job> slot = this.slots[this.current];
        for (Job job : slot) {
            if(job.getCircle() == this.circle){
                // 如果任务的圈数和当前圈数一致则丢进线程池里执行（interval也是一致的，因为slot存放就是该interval的任务）
                this.executor.execute(job.getRunnable());
            }else{
                break;
            }
        }

        // 指针往前一格
        this.current++;
        if(this.current >= this.slotCount){
            // 转完一圈
            this.current = this.current % this.slotCount;
            this.circle++;
        }
    }

    public void init(){
        this.slots = new ArrayList[this.slotCount];

        for (int i = 0; i < this.slots.length; i++) {
            this.slots[i] = new ArrayList<>();
        }

        this.executor = new ThreadPoolExecutor(20, 40, 1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(10));


        this.tick = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(duration);
                    tick();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * 如果时间轮没有启动起来会因为startTime错误
     * @param runnable 定时任务
     * @param date 执行时间
     * @throws Exception
     */
    public void addJob(Runnable runnable, Date date) throws Exception {
        long l = date.getTime() - startTime;

        int circle = 0;
        int interval = (int) (l / duration);

        // 矫正interval
        circle += interval / slotCount;
        interval = interval % slotCount;

        addJob(runnable, circle, interval);

    }

    /**
     *
     * @param runnable 定时任务
     * @param circle 到达圈数
     * @param interval 指针指到位置
     * @throws Exception
     */
    public void addJob(Runnable runnable, int circle, int interval) throws Exception {

        // 验证参数
        if(circle < 0 || interval < 0){
            throw new Exception("参数错误");
        }

        // 矫正interval
        circle += interval / slotCount;
        interval = interval % slotCount;

        Job job = new Job();
        job.setRunnable(runnable);
        job.setInterval(interval);
        job.setCircle(circle);

        List<Job> slot = slots[interval];

        // 维持任务队列的顺序
        int i;
        for (i = slot.size() - 1; i >= 0; i--) {
            if(slot.get(i).getCircle() <= job.getCircle()){
                slot.add(i + 1, job);
                break;
            }
        }

        if(i < 0){
            slot.add(0, job);
        }

    }

    public TimeWheel(int slotCount) {

        this.slotCount = slotCount;
        init();

    }
}
