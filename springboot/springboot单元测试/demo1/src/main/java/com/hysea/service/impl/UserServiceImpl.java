package com.hysea.service.impl;

import com.hysea.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Override
    public void update() {
        try {
            Thread.sleep(200);
            System.out.println("修改成功");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
