package org.egg.integration.erpc.context.extra;

import org.egg.integration.erpc.context.scan.annotation.RemoteCalledScan;
import org.egg.integration.erpc.protocol.ProtocolTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 远程调用上下文, 单例
 * 1. 利用扫描器扫描@RemoteCalled的注解
 * 2. 将RemoteCalled注解的类中的信息以<k, v>形式保存，其中k: beanName，v: ip，port等信息
 */
public class RemoteCalledContext extends ExtraContext{
    private final Map<String, RemoteCalledPacket> contextMap;
    private static RemoteCalledContext context;

    @Override
    public void mount() {
        new RemoteCalledScan(context);
    }

    private RemoteCalledContext() {
        contextMap = new HashMap<>();
    }

    public Map<String, RemoteCalledPacket> getContextMap() {
        return contextMap;
    }

    public static RemoteCalledContext getContext() {
        if(context == null) {
            synchronized (RemoteCalledContext.class) {
                if(context == null) {
                    context = new RemoteCalledContext();
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
