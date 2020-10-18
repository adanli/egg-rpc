package org.egg.integration.erpc.component.proxy;

import org.egg.integration.erpc.context.extra.RemoteCalledContext;
import org.egg.integration.erpc.serialize.packet.Packet;
import org.egg.integration.erpc.serialize.packet.SimplePacket;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 拦截符合条件的方法，并将参数构建成数据包packet
 */
public class RemoteServiceProxy implements InvocationHandler {
    private final static int defaultParameterNumbers = 5;
    private final Object defaultResult = new Object();
    private final Object object;
    private final String beanName;

    public RemoteServiceProxy(Object object, String beanName) {
        this.object = object;
        this.beanName = beanName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(isDeClearedMethod(method.getName())) {
            RemoteCalledContext.RemoteCalledPacket remotePacket = RemoteCalledContext.getContext().getContextMap().get(beanName);
            if(remotePacket == null) {
                throw new RuntimeException("注解中未标注ip、端口等信息");
            }

            /*StringBuilder sb = new StringBuilder("ip: %s\nport: %s\nprotocol: %s\nclass: %s\nmethod: %s\nargs: ");
            Object[] parameters;
            parameters = new Object[defaultParameterNumbers + args.length];

            parameters[0] = remotePacket.getIp();
            parameters[1] = remotePacket.getPort();
            parameters[2] = remotePacket.getProtocol();
            parameters[3] = object.getClass().getName();
            parameters[4] = method.getName();

            for(int i=0; i< args.length; i++) {
                if(i != args.length-1) {
                    sb.append("%s, ");
                } else {
                    sb.append("%s");
                }
                parameters[i+defaultParameterNumbers] = args[i];
            }
            System.out.printf(sb.toString()+"\n", parameters);*/
            Packet packet = packet(remotePacket, method, args);
            packet.execute();
//            RequestQueue.attach(packet);

            return defaultResult;
        }

        return method.invoke(object, args);
    }

    private boolean isDeClearedMethod(String methodName) {
        return Arrays.stream(object.getClass().getDeclaredMethods()).filter(method -> method.getName().equals(methodName)).findFirst().orElse(null) != null;
    }

    private Packet packet(RemoteCalledContext.RemoteCalledPacket standardPacket,
                               Method method, Object[] args) {
        Packet packet = new SimplePacket();
        packet.setRemoteIp(standardPacket.getIp());
        packet.setPort(standardPacket.getPort());
        packet.setProtocol(standardPacket.getProtocol());
        Packet.Content content = new Packet.Content();
        content.setClassName(object.getClass().getName());
        content.setMethodName(method.getName());
        packet.setContent(content);
        return packet;
    }

}
