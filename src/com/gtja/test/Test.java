package com.gtja.test;


import com.gtja.entity.User;

import java.io.File;
import java.lang.reflect.Field;

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

    @org.junit.Test
    public void transform() {
        System.out.print("hello world \\n");
        System.out.print("hello world.");
    }

    @org.junit.Test
    public void reg() {
        /*String test = "abcd1234hijk43434\n";
        boolean matches = test.matches("^abcd1234[^a-g]+\\d+\\n$");
        System.out.println(matches);*/
        String str = "com///abc///";
        System.out.println(str.replaceAll("/+", "/"));
    }

    @org.junit.Test
    public void chars() {
        String s = "AbCdef";
        char[] chars = s.toCharArray();
        chars[0] += 32;
        System.out.println(new String(chars));

    }
    @org.junit.Test
    public void filed() {
        User user = new User();
        Field[] fields = User.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getGenericType().getTypeName());
        }
    }

    @org.junit.Test
    public void split() {
        String str = "com.spring.controller";
        String[] split = str.split("\\.");
        System.out.println(split.length);
        System.out.println(split[split.length-1] + "--->>>");
    }
}
