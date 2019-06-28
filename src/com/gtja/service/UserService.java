package com.gtja.service;

import com.gtja.entity.User;

/**
 * @Author Guyot
 * @Date 2019/6/28 9:01
 */
public interface UserService {
    User register(String name);

    User login(Integer id);
}
