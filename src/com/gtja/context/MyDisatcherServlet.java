package com.gtja.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author Guyot
 * @Date 2019/6/27 17:28
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 0)
public class MyDisatcherServlet extends HttpServlet {
    ClassLoader loader = this.getClass().getClassLoader();
    Properties properties = new Properties();
    String scanPath;
    List clas = new ArrayList();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("hello world");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("DispatcherServlet init...");
        try {
            doLoadConfig();
        } catch (IOException e) {
            System.out.println("加载配置文件失败，请检查文件是否有错误");
        }
        doScanAndInstance(scanPath);
        doDI();
        doHandlerMapping();

    }

    private void doHandlerMapping() {
    }

    private void doDI() {

    }

    private void doScanAndInstance(String path) {
        File file = new File(path);
        if (null == file) {
            return;
        }
        if (!file.isDirectory()) {

        }
        File[] files = file.listFiles();
        for (File f : files) {
            if (!f.isDirectory()) {

            } else {
                doScanAndInstance(f.getPath());
            }
        }
    }

    /**
     *
     * @throws IOException
     */
    private void doLoadConfig() throws IOException {
        InputStream in = loader.getResourceAsStream("com/gtja/spring.properties");
        properties.load(in);
        String scanPackage = (String) properties.get("scanPackage");
        scanPath= loader.getResource("") + scanPackage.replaceAll("\\.", "/");
    }

}
