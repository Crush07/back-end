package com.hysea.controller;

import com.hysea.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserService userService;

    @Test
    void update() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                // 调用服务接口
                userService.update();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        executor.shutdown();
    }
}