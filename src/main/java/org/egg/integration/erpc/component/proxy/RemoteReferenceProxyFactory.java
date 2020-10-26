package org.egg.integration.erpc.component.proxy;

import org.egg.integration.erpc.context.extra.RemoteReferenceContext;
import org.egg.integration.erpc.protocol.Protocol;
import org.egg.integration.erpc.protocol.ProtocolTypeEnum;

import java.util.Map;

/**
 * 远程调用的代理工厂
 * 1. 获取当前对象
 *      发送CONNECT信息，去对端服务器上获取目标类
 * 2. 将目标对象用代理封装并返回
 */
public class RemoteReferenceProxyFactory {
    private final static Map<String, Object> context;
    private static final Object REMOTE_REFERENCE_PROXY_FACTORY_LOCK = new Object();

    static {
        context = RemoteReferenceContext.getContext().getContextMap();
    }

    public static Object create(String beanName) {
        Object object = context.get(beanName);
        if(object != null) {
            return object;
        }
        // 远程访问并设值
        synchronized (REMOTE_REFERENCE_PROXY_FACTORY_LOCK) {
            if(context.get(beanName) == null) {
                object = getFromRemote(beanName);
                context.put(beanName, object);
            }

        }
        return object;
    }

    private static Object getFromRemote(String beanName) {
        RemoteReferenceContext.RemoteCalledPacket packet = new RemoteReferenceContext.RemoteCalledPacket();
        packet.setIp("localhost");
        packet.setPort(12200);
        packet.setProtocol(ProtocolTypeEnum.ERPC);
    }

}
