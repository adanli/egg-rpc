package org.egg.integration.erpc.context.scan.annotation;

import org.egg.integration.erpc.component.proxy.RemoteCallProxyFactory;
import org.egg.integration.erpc.constant.TemporaryConstant;
import org.egg.integration.erpc.context.BeanContext;
import org.egg.integration.erpc.context.Context;
import org.egg.integration.erpc.context.extra.RemoteServiceContext;
import org.egg.integration.erpc.context.scan.AbstractScan;
import org.egg.integration.erpc.context.util.BeanNameUtils;
import org.egg.integration.erpc.context.util.BeanUtils;
import org.egg.integration.erpc.protocol.ProtocolTypeEnum;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RemoteServiceScan extends AbstractScan {
    private static Context context;
    private final String remoteCalledScanCoverage = TemporaryConstant.EGG_RPC_COMPONENT_SCAN;
    private final static String ANNOTATION_SERVICE = "org.egg.integration.erpc.annotation.RemoteService";

    public RemoteServiceScan(RemoteServiceContext context) {
        super(context);
        super.setScanCoverage(remoteCalledScanCoverage);
        super.setAnnotationStr(ANNOTATION_SERVICE);
        RemoteServiceScan.context = context;
        super.init();
    }

    /**
     * 包装远程调用信息，替换原生的bean
     * 1. 将注解中的ip、端口、协议等信息封装，并缓存到当前的上下文context中
     * 2. 替换beanContext中的该bean，使得当前bean具有代理扩展能力
     * @param className 类的全路径
     */
    @Override
    protected void handleClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Annotation annotation = Arrays.stream(clazz.getAnnotations()).filter(anno -> anno.annotationType().getName().equals(ANNOTATION_SERVICE)).findFirst().orElse(null);
            if(annotation != null) {
                Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
                if(annotationMethods.length == 0) return;
                RemoteServiceContext.RemoteCalledPacket packet = new RemoteServiceContext.RemoteCalledPacket();
                for(Method annotationMethod: annotationMethods) {
                    try {
                        if(annotationMethod.getName().equals("ip")) {
                            packet.setIp(String.valueOf(annotationMethod.invoke(annotation)));
                        } else if(annotationMethod.getName().equals("port")) {
                            packet.setPort((Integer) annotationMethod.invoke(annotation));
                        } else if(annotationMethod.getName().equals("protocol")) {
                            packet.setProtocol((ProtocolTypeEnum) annotationMethod.invoke(annotation));
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                String beanName = BeanNameUtils.beautifyBeanName(className);
                if(context instanceof RemoteServiceContext) {
                    ((RemoteServiceContext)context).getContext().getContextMap().put(beanName, packet);
                } else {
                    throw new RuntimeException("设置的context类型出错");
                }
//                setProxyBean(beanName, clazz);
                setBean(beanName, clazz);
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setBean(String beanName, Class<?> clazz) {
        Object object = BeanUtils.create(clazz);
        if(object != null) {
            BeanContext.getBeanContext().getContextMap().putIfAbsent(beanName, object);
        }
    }

    private void setProxyBean(String beanName, Class<?> clazz) {
        Object object = RemoteCallProxyFactory.create(beanName, clazz);
        if(object != null) {
            synchronized (BeanContext.BEAN_CONTEXT_LOCK) {
                BeanContext.getBeanContext().getContextMap().put(beanName, object);
            }
        }

    }

}
