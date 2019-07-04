package com.gtja.component.controller;

import com.gtja.annotation.MyAutowired;
import com.gtja.annotation.MyController;
import com.gtja.annotation.MyRequestMapping;
import com.gtja.component.service.ProductService;

/**
 * @author Guyot
 * @date 2019/7/4 9:23
 */
@MyController
@MyRequestMapping("/product")
public class ProductController {
    @MyAutowired
    private ProductService productService;

}
