package org.egg.integration.erpc.context.scan;

import org.egg.integration.erpc.context.Context;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;

/**
 * 基础的扫描类
 * 一个子类只能够扫描一个注解
 * // fixme 后续会考虑改成扫描一类注解
 */
public abstract class AbstractScan {
    /**
     * 调用该扫描能力的上下文
     */
    protected Context context;
    /**
     * 扫描的目录范围
     */
    private String scanCoverage;

    private String annotationStr;

    public void setScanCoverage(String scanCoverage) {
        this.scanCoverage = scanCoverage;
    }

    public void setAnnotationStr(String annotationStr) {
        this.annotationStr = annotationStr;
    }


    public AbstractScan(Context context) {
        this.context = context;
    }

    protected void init() {
        scan();
    }

    protected void scan() {
        String path = scanCoverage.replaceAll("\\.", "/");
        try {
            Enumeration<URL> enumeration = Thread.currentThread().getContextClassLoader().getResources(path);
            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                if(url.getProtocol().equals("file")) {
                    // 解析文件，提取class类
                    findClasses(scanCoverage, url.getPath());
                } else {
                    // fixme
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    private void findClasses(String pkg, String path) {
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
                if(isTargetClass(className, annotationStr)) {
                    handleClass(className);
                }
                Field targetField = isTargetField(className, annotationStr);
                if(targetField != null) {
                    handleField(targetField);
                }
            }
        }
    }

    /**
     * 判断当前实现类是否是被注解标注的类
     * @param className 类的全路径
     * @param annotationStr 目标注解
     * @return 是否被注解标注的类
     */
    protected boolean isTargetClass(String className, String annotationStr) {
        try {
            Class<?> clazz = Class.forName(className);
            if(!clazz.isInterface()) {
                return hasAnnotation(clazz.getAnnotations(), annotationStr, new HashSet<>());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    };

    /**
     * 判断注解中是否包含目标注解, 如果包含则返回
     * @param annotations 注解列表
     * @param annotationStr 目标注解值
     * @return 是否包含目标注解
     */
    protected static boolean hasAnnotation(Annotation[] annotations, String annotationStr, HashSet<String> set) {
        for(Annotation annotation: annotations) {
            if(annotation.annotationType().getName().equals(annotationStr)) {
                return true;
            }
            if(!set.contains(annotation.annotationType().getName())) {
                set.add(annotation.annotationType().getName());
                if(annotation.annotationType().getAnnotations().length>0
                        && hasAnnotation(annotation.annotationType().getAnnotations(), annotationStr, set)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 处理当前类
     * @param className 类的全路径
     */
    protected void handleClass(String className) {};

    /**
     * 判断目标类的属性中是否包含目标注解
     * @param className 类全路径
     * @return 属性名的全路径
     */
    protected Field isTargetField(String className, String annotationStr) {
        return null;
    };

    /**
     * 对属性的信息进行处理
     * @param field 属性名的全路径
     */
    protected  void handleField(Field field) {

    };

}
