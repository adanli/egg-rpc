package org.egg.integration.erpc.component.proxy;


import org.egg.integration.erpc.context.BeanContext;
import org.egg.integration.erpc.context.util.BeanNameUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 提供单例工厂
 */
public class RemoteCallProxyFactory {
    private static Map<String, Object> context;

    protected RemoteCallProxyFactory(){
    }

    static {
        context = BeanContext.getBeanContext().getContextMap();
    }

    /**
     * 从代理工厂中获得当前类
     * @param beanName bean实例对象的名称
     * @return
     */
    public static Object getBean(String beanName) {
        Object object = context.get(beanName);
        if(object == null) {
            throw new RuntimeException(String.format("当前bean %s不存在", beanName));
        }

        return object;
    }

    public static Object create(String beanName, Class<?> clazz) {
        try {
            Object object = clazz.newInstance();
            InvocationHandler proxyObject = new RemoteServiceProxy(object, beanName);
            return Proxy.newProxyInstance(proxyObject.getClass().getClassLoader(), object.getClass().getInterfaces(), proxyObject);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
