package org.egg.integration.erpc.context.scan.annotation;

import org.egg.integration.erpc.constant.TemporaryConstant;
import org.egg.integration.erpc.context.Context;
import org.egg.integration.erpc.context.extra.RemoteCalledContext;
import org.egg.integration.erpc.context.scan.AbstractScan;
import org.egg.integration.erpc.context.util.BeanNameUtils;
import org.egg.integration.erpc.protocol.ProtocolTypeEnum;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RemoteCalledScan extends AbstractScan {
    private static Context context;
    private final String remoteCalledScanCoverage = TemporaryConstant.EGG_RPC_COMPONENT_SCAN;
    private final static String ANNOTATION_SERVICE = "org.egg.integration.erpc.annotation.RemoteCalled";

    public RemoteCalledScan(RemoteCalledContext context) {
        super(context);
        super.setScanCoverage(remoteCalledScanCoverage);
        super.setAnnotationStr(ANNOTATION_SERVICE);
        RemoteCalledScan.context = context;
        super.init();
    }

    @Override
    protected void handleClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Annotation annotation = Arrays.stream(clazz.getAnnotations()).filter(anno -> anno.annotationType().getName().equals(ANNOTATION_SERVICE)).findFirst().orElse(null);
            if(annotation != null) {
                Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
                if(annotationMethods.length == 0) return;
                RemoteCalledContext.RemoteCalledPacket packet = new RemoteCalledContext.RemoteCalledPacket();
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
                if(context instanceof RemoteCalledContext) {
                    ((RemoteCalledContext)context).getContext().put(beanName, packet);
                } else {
                    throw new RuntimeException("设置的context类型出错");
                }
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
