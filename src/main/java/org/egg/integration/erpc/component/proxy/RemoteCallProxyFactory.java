package org.egg.integration.erpc.component.proxy;


import org.egg.integration.erpc.context.util.BeanNameUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供单例工厂
 */
public class RemoteCallProxyFactory {
    private static final Map<String, Object> context = new HashMap<>();
    private static final Object REMOTE_CALL_PROXY_FACTORY_LOCK = new Object();

    protected RemoteCallProxyFactory(){

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

    /**
     * 向上下文context中保存bean
     * @param className 对象全路径
     */
    public static void setBean(String className) {
        Object object = create(className);
        String beanName = BeanNameUtils.beautifyBeanName(className);
        setBean(beanName, object);
    }

    public static void setBean(String beanName, Object bean) {
        synchronized (REMOTE_CALL_PROXY_FACTORY_LOCK) {
            context.put(beanName, bean);
        }
    }

    /**
     * 根据类的全路径，反射生成对象，并把对象保存到上下文context中
     * @param className 类的全路径
     * @return
     */
    protected static Object create(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Object object = clazz.newInstance();
            InvocationHandler proxyObject = new RemoteServiceProxy(object);
            return Proxy.newProxyInstance(proxyObject.getClass().getClassLoader(), object.getClass().getInterfaces(), proxyObject);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void display() {
        context.forEach((k, v) -> {
            System.out.println(String.format("bean %s has exist in context", k));
        });
    }

    /**
     * 将类的全路径转换成接口的形式名称，如DemoServiceImpl -> demoService
     * @param className 类的全路径
     * @return
     */
    /*private static String beautifyBeanName(String className) {
        int startPosition = className.lastIndexOf('.');
        int endPosition = className.lastIndexOf("Impl");
        if(endPosition > -1) {
            return ((char)(className.charAt(startPosition+1)+32)) + className.substring(startPosition+2, endPosition);
        }
        if(startPosition > -1) {
            return ((char)(className.charAt(startPosition+1)+32)) + className.substring(startPosition+2);
        }
        return className;
    }*/

}
