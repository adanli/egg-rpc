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
import java.util.Map;

public class AnnotationBeanScan extends AbstractScan {
    private static Map<String, Object> contextMap;
    private final String beanScanConverage = TemporaryConstant.EGG_RPC_COMPONENT_SCAN;

    public AnnotationBeanScan(BeanContext context) {
        super(context);
//        super.context = context;
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
                annotation(className);
                Object object = create(className);
                String beanName = BeanNameUtils.beautifyBeanName(className);
                contextMap.put(beanName, object);
            }
        }
    }

    private static void annotation(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Annotation[] annotations = clazz.getAnnotations();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
