package org.egg.integration.erpc.context.scan.annotation;

import org.egg.integration.erpc.component.proxy.RemoteCallProxyFactory;
import org.egg.integration.erpc.constant.TemporaryConstant;
import org.egg.integration.erpc.context.BeanContext;
import org.egg.integration.erpc.context.extra.RemoteReferenceContext;
import org.egg.integration.erpc.context.scan.AbstractScan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 扫描标注了@RemoteReference注解的属性，并将被注解的接口保存到RemoteReferenceContext上下文中
 * 注入注解中的信息，并以消息的方式传播出去
 */
public class RemoteReferenceScan extends AbstractScan {
    private RemoteReferenceContext context;

    public RemoteReferenceScan(RemoteReferenceContext context) {
        super(context);
        super.setAnnotationStr("org.egg.integration.erpc.annotation.RemoteReference");
        super.setScanCoverage(TemporaryConstant.EGG_RPC_COMPONENT_SCAN);
        this.context = context;
        super.init();
    }


    @Override
    protected Field isTargetField(String className, String annotationStr) {
        // fixme 多次对同一个className进行反射，会消耗大量性能，这里后续可以考虑使用一个缓存来处理，等初始化完成后将该缓存回收
        try {
            Class<?> clazz = Class.forName(className);
            Field[] fields = clazz.getDeclaredFields();
            for(Field field: fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                for(Annotation annotation: annotations) {
                    if (annotation.annotationType().getName().equals(annotationStr)) {
                        return field;
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void handleField(Field field) {
        Object object = RemoteCallProxyFactory.create(field.getType().getName(), field.getType());
        Object obj = BeanContext.getBeanContext().getContextMap().get("demoReferenceService");
        try {
            field.setAccessible(true);
            field.set(obj, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        context.getContextMap().putIfAbsent(field.getType().getName(), object);
    }
}
