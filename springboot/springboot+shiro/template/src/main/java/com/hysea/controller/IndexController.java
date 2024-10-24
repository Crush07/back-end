package com.hysea.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@CrossOrigin
public class IndexController {

    /**
     * curl -i http://localhost:8081/index
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "index"; // 返回index.html
    }

    /**
     * curl -i http://localhost:8081/login
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login"; // 返回index.html
    }
}