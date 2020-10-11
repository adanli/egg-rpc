package org.egg.integration.erpc.context;



import org.egg.integration.erpc.context.scan.annotation.AnnotationBeanScan;

import java.util.HashMap;
import java.util.Map;

public class BeanContext extends Context{
    private static BeanContext context;
    private Map<String, Object> contextMap = new HashMap<>();

    private BeanContext() {
        new AnnotationBeanScan(this);
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
