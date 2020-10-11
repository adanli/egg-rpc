package org.egg.integration.erpc.context;


import java.util.ArrayList;
import java.util.List;

public class EggContext extends Context{
    private static List<Context> contexts = new ArrayList<>();
    private static Context beanContext;
    private static List<Context> extraContexts;

    static {
        init();
    }

    public static void init() {
        beanContext = BeanContext.getBeanContext();
        contexts.add(beanContext);
        if(extraContexts != null) {
            contexts.addAll(extraContexts);
        }
    }

    public static void setExtraContext(List<Context> contexts) {
        extraContexts = contexts;
    }

    public static void setBeanContext(Context beanContext) {
        EggContext.beanContext = beanContext;
    }

    public static Context getBeanContext() {
        return beanContext;
    }

    public static List<Context> getExtraContexts() {
        return extraContexts;
    }

    public static void show() {
        if(beanContext instanceof BeanContext) {
            BeanContext beanContext1 = (BeanContext) beanContext;
            beanContext1.getContextMap().forEach((k, v) -> {
                System.out.println(String.format("%s 已经在加载到资源池", k));
            });
        }
    }

}
