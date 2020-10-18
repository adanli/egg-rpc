package org.egg.integration.erpc.context;



import org.egg.integration.erpc.context.scan.annotation.AnnotationBeanScan;

import java.util.HashMap;
import java.util.Map;

public class BeanContext extends Context{
    private static BeanContext context;
    private final Map<String, Object> contextMap = new HashMap<>();
    public final static Object BEAN_CONTEXT_LOCK = new Object();

    private BeanContext() {
    }

    @Override
    public void mount() {
        new AnnotationBeanScan(context);
    }

    public static BeanContext getBeanContext() {
        if(context != null) {
            return context;
        }
        synchronized (BeanContext.class) {
            if(context == null) {
                context = new BeanContext();
            }
        }
        return context;
    }

    public Map<String, Object> getContextMap() {
        return contextMap;
    }

}
