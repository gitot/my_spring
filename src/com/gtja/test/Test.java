package com.gtja.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author Guyot
 * @Date 2019/6/28 9:27
 */
public class Test {
    @org.junit.Test
    public void file() {
        String path = "D:\\test";
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getName());
        System.out.println(file.getPath());
    }

    @org.junit.Test
    public void replaceAll() {
        String a = "com.gtja";
        String s = a.replaceAll("\\.", "/");
        System.out.println(a);
        System.out.println(s);
    }

    @org.junit.Test
    public void resource() {
        System.out.println(Test.class.getClassLoader().getResource("").getFile());
        System.out.println(Test.class.getClassLoader().getResource("").getPath());


    }
}
