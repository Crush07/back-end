package com.hysea.controller;

import com.hysea.entity.User;
import com.hysea.event.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

/**
 * @eo.api-type http
 * @eo.groupName 默认分组
 * @eo.path /user
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    /**
     * curl -X PUT http://localhost:8081/user/update -H "Content-Type: application/json" -d "{\"userName\":\"hyy\"}"
     * @param user
     * @return
     */
    @PutMapping("/update")
    public String update(@RequestBody User user){

        System.out.println("修改用户！");

        applicationEventPublisher.publishEvent(new UserEvent<>(user));
        return "修改用户成功";
    }

}
