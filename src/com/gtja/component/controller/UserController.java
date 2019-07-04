package com.gtja.component.controller;

import com.gtja.annotation.MyAutowired;
import com.gtja.annotation.MyController;
import com.gtja.annotation.MyRequestMapping;
import com.gtja.entity.User;
import com.gtja.component.service.UserService;

/**
 * @Author Guyot
 * @Date 2019/6/27 17:18
 */
@MyController
@MyRequestMapping("/user")
public class UserController {


    @MyAutowired
    private UserService userService;

    @MyRequestMapping("/register")
    public User register(String name) {
        System.out.println("接受到前台的注册请求，name:" + name);
        User user = userService.register(name);
        return user;
    }

}
