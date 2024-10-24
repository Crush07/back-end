package com.hysea.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        return "admin/login"; // 返回index.html
    }



    /**
     * curl -i http://localhost:8081/login
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public String toLogin() {

        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                "123456",
                "123456"
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            return "用户名不存在！";
        } catch (AuthenticationException e) {
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            return "没有权限";
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","success");
        return jsonObject.toJSONString();
    }
}