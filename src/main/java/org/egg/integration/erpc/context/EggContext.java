package org.egg.integration.erpc.context;

import org.egg.integration.erpc.context.extra.RemoteCalledContext;

import java.util.ArrayList;
import java.util.List;

public class EggContext extends Context{
    private final static List<Context> contexts = new ArrayList<>();
    private static Context beanContext;
    private static List<Context> extraContexts;

    private EggContext() {

    }

    static {
        initRemoteCalledContext();
        init();
        new EggContext();
    }

    {
        mount();
    }

    private static void initRemoteCalledContext() {
        List<Context> list = new ArrayList<>(1);
        list.add(RemoteCalledContext.getContext());
        setExtraContext(list);
    }

    public static void setExtraContexts(List<Context> extraContexts) {
        if(EggContext.extraContexts == null) {
            EggContext.extraContexts = new ArrayList<>();
        }
        EggContext.extraContexts.addAll(extraContexts);
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
        for(Context context: contexts) {
            if(context instanceof BeanContext) {
                ((BeanContext)context).getContextMap().forEach((k, v) -> {
                    System.out.printf("%s: %s 已经在加载到资源池\n", k, v);
                });
            } else if(context instanceof RemoteCalledContext) {
                System.out.println("RemoteCalledContext: ");
                ((RemoteCalledContext)context).getContext().getContextMap().forEach((k, v) -> {
                    System.out.printf("\t%s: %s\n", k, v);
                });
            }
        }


    }

    @Override
    public void mount() {
        for(Context context: contexts) {
            context.mount();
        }
    }
}
