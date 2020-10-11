package org.egg.integration.erpc.component.scan;


import org.egg.integration.erpc.component.proxy.RemoteCallProxyFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;

/**
 * 在项目启动的时候，扫描所有的@RemoteService注解
 */
public class AnnotationBeanAnnotationScan extends AbstractAnnotationScan {
    private final static String componentScan = "org.egg.integration.erpc";

    @Override
    protected void scan() {
        initScanRemoteService();
    }

    /**
     * 扫描componentScan配置的目录下所有的代码类，并提取所有打了@RemoteService注解的类
     */
    private static void initScanRemoteService() {
        System.out.println("扫描所有配置了@RemoteService的注解");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String scanPath = componentScan.replaceAll("\\.", "/");

        try {
            Enumeration<URL> enumeration = classLoader.getResources(scanPath);
            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                if(url.getProtocol().equals("file")) {
                    // 解析出所有的.class文件
                    findClasses(componentScan, url.getPath());
                } else {
                    System.out.println(url.getProtocol() + "..." + url.getPath());
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void findClasses(String pkg, String path) {
        File file = new File(path);
        if(file.isDirectory()) {
            String[] files = file.list();
            if(files != null) {
                for(String f: files) {
                    findClasses(pkg+"."+f, path+"/"+f);
                }
            }
        } else {
            if(pkg.endsWith(".class")) {
                String className = pkg.substring(0, pkg.lastIndexOf(".class"));
                set(className);
            }
        }
    }

    /**
     * 反射拿到当前类，并过滤出使用了RemoteService注解的类
     * 如果识别到是被RemoteService注解修饰的类，使用发射 + 简单工厂模式，创建一个带有注解含义的类
     * @param className 类的全路径
     */
    private static void set(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Annotation[] annotations = clazz.getAnnotations();
            for(Annotation annotation: annotations) {
                if(annotation.annotationType().getTypeName().equals("org.egg.integration.erpc.annotation.RemoteService")) {
                    RemoteCallProxyFactory.setBean(className);
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



}
