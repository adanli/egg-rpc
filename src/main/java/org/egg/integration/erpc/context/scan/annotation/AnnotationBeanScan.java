package org.egg.integration.erpc.context.scan.annotation;

import org.egg.integration.erpc.constant.TemporaryConstant;
import org.egg.integration.erpc.context.BeanContext;
import org.egg.integration.erpc.context.scan.AbstractScan;
import org.egg.integration.erpc.context.util.BeanNameUtils;

import java.util.Map;

public class AnnotationBeanScan extends AbstractScan {
    private static Map<String, Object> contextMap;
    private final String beanScanConverage = TemporaryConstant.EGG_RPC_COMPONENT_SCAN;
    private final static String ANNOTATION_SERVICE = "org.egg.integration.erpc.annotation.Service";

    public AnnotationBeanScan(BeanContext context) {
        super(context);
        super.setAnnotationStr(ANNOTATION_SERVICE);
        super.setScanCoverage(beanScanConverage);
        contextMap = context.getContextMap();
        super.init();
    }

    @Override
    protected void handleClass(String className) {
        Object object = create(className);
        String beanName = BeanNameUtils.beautifyBeanName(className);
//        contextMap.putIfAbsent(beanName, object);
        if(contextMap.get(beanName) == null) {
            synchronized (BeanContext.BEAN_CONTEXT_LOCK) {
                contextMap.putIfAbsent(beanName, object);
            }
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
