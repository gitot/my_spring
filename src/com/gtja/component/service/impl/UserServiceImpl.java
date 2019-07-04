package com.gtja.component.service.impl;

import com.gtja.annotation.MyService;
import com.gtja.component.service.UserService;
import com.gtja.entity.User;

import java.util.Random;

/**
 * @Author Guyot
 * @Date 2019/6/28 9:14
 */
@MyService
public class UserServiceImpl implements UserService {
    public User register(String name) {
        User user = new User();
        user.setId(new Random().nextInt(10000));
        System.out.print("新用户：" + user + " 注册成功");
        return user;
    }

    public User login(Integer id) {
        return null;
    }
}
