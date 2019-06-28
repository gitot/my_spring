package com.gtja.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @Author Guyot
 * @Date 2019/6/28 9:27
 */
public class Test {
    public static void main(String[] args) throws IOException {


        /*URL resource = Test.class.getClassLoader().getResource("com/gtja/controller");
        String strFile = resource.getFile();
        File file = new File(strFile);
        File[] files = file.listFiles();
        for (File file1 : files) {
            System.out.println(file1.toString());
        }*/

        String s = "com.gtja.controller";
        String resource = Test.class.getClassLoader().getResource("").getPath()+s.replaceAll("\\.", "/");
        System.out.println(resource);

        File file = new File(resource);
        File[] files = file.listFiles();
        for (File file1 : files) {
            System.out.println(file1.getName());
            System.out.println(file1.getPath());
            System.out.println(file1.getAbsoluteFile());
            System.out.println(file1.getAbsolutePath());
            System.out.println(file1.getParent());
            System.out.println(file1.getParentFile());

        }
    }
}
