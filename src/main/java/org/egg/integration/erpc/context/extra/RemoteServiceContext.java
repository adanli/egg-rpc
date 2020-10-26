package org.egg.integration.erpc.context.extra;

import org.egg.integration.erpc.context.scan.annotation.RemoteServiceScan;
import org.egg.integration.erpc.protocol.ProtocolTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 远程调用上下文, 单例
 * 1. 利用扫描器扫描@RemoteCalled的注解
 * 2. 将RemoteCalled注解的类中的信息以<k, v>形式保存，其中k: beanName，v: ip，port等信息
 */
public class RemoteServiceContext extends ExtraContext{
    private final Map<String, RemoteCalledPacket> contextMap;
    private static RemoteServiceContext context;

    @Override
    public void mount() {
        new RemoteServiceScan(context);
    }

    private RemoteServiceContext() {
        contextMap = new HashMap<>();
    }

    public Map<String, RemoteCalledPacket> getContextMap() {
        return contextMap;
    }

    public static RemoteServiceContext getContext() {
        if(context == null) {
            synchronized (RemoteServiceContext.class) {
                if(context == null) {
                    context = new RemoteServiceContext();
                }
            }
        }
        return context;
    }


    public static class RemoteCalledPacket {
        private String ip;
        private int port;
        private ProtocolTypeEnum protocol;

        public RemoteCalledPacket() {

        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public void setProtocol(ProtocolTypeEnum protocol) {
            this.protocol = protocol;
        }

        @Override
        public String toString() {
            return "RemoteCalledPacket{" +
                    "ip='" + ip + '\'' +
                    ", port=" + port +
                    ", protocol=" + protocol +
                    '}';
        }

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }

        public ProtocolTypeEnum getProtocol() {
            return protocol;
        }
    }

}
