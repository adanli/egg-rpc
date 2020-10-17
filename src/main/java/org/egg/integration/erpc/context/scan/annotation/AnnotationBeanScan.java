package org.egg.integration.erpc.context.scan.annotation;

import org.egg.integration.erpc.constant.TemporaryConstant;
import org.egg.integration.erpc.context.BeanContext;
import org.egg.integration.erpc.context.scan.AbstractScan;
import org.egg.integration.erpc.context.util.BeanNameUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;

public class AnnotationBeanScan extends AbstractScan {
    private static Map<String, Object> contextMap;
    private final String beanScanConverage = TemporaryConstant.EGG_RPC_COMPONENT_SCAN;
    private final static String ANNOTATION_SERVICE = "org.egg.integration.erpc.annotation.Service";

    public AnnotationBeanScan(BeanContext context) {
        super(context);
        contextMap = context.getContextMap();
        scan();
    }

    {
//        scan();
    }

    /**
     * 扫描带有@Service注解的类，保存到contextMap中
     */
    @Override
    protected void scan() {
        String path = beanScanConverage.replaceAll("\\.", "/");
        try {
            Enumeration<URL> enumeration = Thread.currentThread().getContextClassLoader().getResources(path);
            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                if(url.getProtocol().equals("file")) {
                    // 解析文件，提取class类
                    findClasses(beanScanConverage, url.getPath());
                } else {
                    // fixme
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
                // fixme 根据反射去分析Class的注解
                if(isServiceClass(className)) {
                    Object object = create(className);
                    String beanName = BeanNameUtils.beautifyBeanName(className);
                    contextMap.put(beanName, object);
                }
            }
        }
    }

    /**
     * 判断当前注解是否Service
     * 判断，如果当前文件是个类，递归查询注解中是否包含Service
     * @return 是否注解Service
     */
    private static boolean isServiceClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if(!clazz.isInterface()) {
                return hasAnnotation(clazz.getAnnotations(), ANNOTATION_SERVICE, new HashSet<>());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断注解中是否包含目标注解, 如果包含则返回
     * @param annotations 注解列表
     * @param annotationStr 目标注解值
     * @return 是否包含目标注解
     */
    private static boolean hasAnnotation(Annotation[] annotations, String annotationStr, HashSet<String> set) {
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

    private static Object create(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
