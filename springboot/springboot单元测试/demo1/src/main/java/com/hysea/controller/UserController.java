package com.hysea.controller;

import com.hysea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    UserService userService;

    /**
     * curl -X GET http://localhost:8081/user/update"
     * @return
     */
    @GetMapping("/update")
    public String update(){

        System.out.println("修改用户！");
        return "修改用户成功";
    }

}
