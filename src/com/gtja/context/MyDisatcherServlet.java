package com.gtja.context;

import com.gtja.annotation.MyAutowired;
import com.gtja.annotation.MyController;
import com.gtja.annotation.MyRequestMapping;
import com.gtja.annotation.MyService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author Guyot
 * @Date 2019/6/27 17:28
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 0)
public class MyDisatcherServlet extends HttpServlet {
    private ClassLoader loader = this.getClass().getClassLoader();
    private Properties properties = new Properties();
    private String rootPath;
    private List<String> classNames = new ArrayList();
    private Map<String, Object> ioc = new HashMap<>();
    private Map<String, Method> handlerMapping = new HashMap<>();

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
        try {
            doInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("class not found!");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        try {
            doDI();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("di exception!");
        }
        doHandlerMapping();

    }

    private void doHandlerMapping() {
        System.out.println("handler mapping start ...");
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            String url = "";
            if (entry.getValue().getClass().isAnnotationPresent(MyRequestMapping.class)) {
                MyRequestMapping mapping = entry.getValue().getClass().getDeclaredAnnotation(MyRequestMapping.class);
                url += mapping;
            }
            Method[] methods = entry.getValue().getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(MyRequestMapping.class)) {
                    MyRequestMapping methodMapping = method.getAnnotation(MyRequestMapping.class);
                    url +=methodMapping;
                    url = url.replaceAll("/+", "/");
//                    handlerMapping.put(url, entry.getValue());

                }
            }
        }
        System.out.println("handler mapping end ...");
    }

    private void doDI() throws IllegalAccessException {
        System.out.println("di start ...");
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Object value = entry.getValue();
            Class<?> aClass = value.getClass();
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                boolean isAnnotation = field.isAnnotationPresent(MyAutowired.class);
                if (isAnnotation) {
                    field.set(value, ioc.get(getBeanName(field.getGenericType().getTypeName())));
                }
            }
        }
        System.out.println("di end...");
    }

    private String getBeanName(String str) {
        if (str == null) {
            return null;
        }
        String[] split = str.split("\\.");
        return toLowerFirstCase(split[split.length - 1]);
    }

    private void doInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println("instance start...");
        if (null == classNames) {
            return;
        }
        for (String claz : classNames) {
            Class<?> clz = Class.forName(claz);
            if (clz.isAnnotationPresent(MyController.class)) {
                ioc.put(toLowerFirstCase(clz.getSimpleName()), clz.newInstance());
            }
            if (clz.isAnnotationPresent(MyService.class)) {
                Class<?>[] interfaces = clz.getInterfaces();
                for (Class<?> inter : interfaces) {
                    ioc.put(toLowerFirstCase(inter.getSimpleName()), clz.newInstance());
                    System.out.println("class " + clz.getName() + "is instanced");
                }
            }
        }
        System.out.println("instance end...");
    }

    public String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return new String(chars);
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
        System.out.println("load config file start...");
        InputStream in = loader.getResourceAsStream("com/gtja/spring.properties");
        properties.load(in);
        rootPath = ((String) properties.get("pacakage-sacan"));
        System.out.println("load config file end , package-scan=" + rootPath);
    }

}
