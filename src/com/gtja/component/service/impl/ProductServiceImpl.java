package com.gtja.component.service.impl;

import com.gtja.annotation.MyService;
import com.gtja.component.service.ProductService;

/**
 * @author Guyot
 * @date 2019/7/4 9:25
 */
@MyService
public class ProductServiceImpl implements ProductService {
    public void add(String name) {
        System.out.println("添加商品：" + name);
    }
}
