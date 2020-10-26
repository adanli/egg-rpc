package org.egg.integration.erpc.component.proxy;

import org.egg.integration.erpc.context.extra.RemoteReferenceContext;
import org.egg.integration.erpc.serialize.packet.Packet;
import org.egg.integration.erpc.serialize.packet.SimplePacket;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RemoteReferenceProxy implements InvocationHandler, Serializable {
    private final static int defaultParameterNumbers = 5;
    private final Object defaultResult = new Object();
    private final Class<?> type;

    public RemoteReferenceProxy(Class<?> type) {
        this.type = type;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(isDeClearedMethod(method.getName())) {
            RemoteReferenceContext.RemoteCalledPacket remotePacket = RemoteReferenceContext.getContext().getPacket().get(type.getName());
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

        return method.invoke(this, args);
    }

    private boolean isDeClearedMethod(String methodName) {
        return Arrays.stream(type.getDeclaredMethods()).filter(method -> method.getName().equals(methodName)).findFirst().orElse(null) != null;
    }

    private Packet packet(RemoteReferenceContext.RemoteCalledPacket standardPacket,
                          Method method, Object[] args) {
        Packet packet = new SimplePacket();
        packet.setDestIp(standardPacket.getIp());
        packet.setDestPort(standardPacket.getPort());
        packet.setProtocol(standardPacket.getProtocol());
        Packet.Content content = new Packet.Content();
        content.setClassName(type.getName());
        content.setMethodName(method.getName());
        packet.setContent(content);
        return packet;
    }
}
