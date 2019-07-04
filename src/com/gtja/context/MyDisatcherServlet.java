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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author Guyot
 * @Date 2019/6/27 17:28
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 0)
public class MyDisatcherServlet extends HttpServlet {
    private ClassLoader loader = this.getClass().getClassLoader();
    private Properties properties = new Properties();
    private String rootPath;
    private List classNames = new ArrayList();

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
        doScan(rootPath);
        doInstance();
        doDI();
        doHandlerMapping();

    }

    private void doHandlerMapping() {
    }

    private void doDI() {

    }

    private void doInstance() {

    }

    private void doScan(String path) {
        if (null == path) {
            return;
        }
        File file = new File(loader.getResource(path.replaceAll("\\.", "/")).getFile());
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                doScan(path + "." + f.getName());
            }
            if (f.getName().endsWith(".class")) {
                classNames.add(path + "." + f.getName().replaceAll(".class", ""));
            }
        }


    }

    /**
     * 加载配置文件，获取扫描的包
     *
     * @throws IOException
     */
    private void doLoadConfig() throws IOException {
        InputStream in = loader.getResourceAsStream("com/gtja/spring.properties");
        properties.load(in);
        rootPath = ((String) properties.get("pacakage-sacan"));
    }

}
