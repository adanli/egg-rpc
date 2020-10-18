package org.egg.integration.erpc.context.extra;

import org.egg.integration.erpc.context.scan.annotation.RemoteCalledScan;
import org.egg.integration.erpc.protocol.ProtocolTypeEnum;
import sun.jvm.hotspot.debugger.remote.ppc.RemotePPCThread;

import java.util.HashMap;
import java.util.Map;

/**
 * 远程调用上下文
 * 1. 利用扫描器扫描@RemoteCalled的注解
 * 2. 将RemoteCalled注解的类中的信息以<k, v>形式保存，其中k: beanName，v: ip，port等信息
 */
public class RemoteCalledContext extends ExtraContext{
    private final Map<String, RemoteCalledPacket> context;

    public RemoteCalledContext() {
        context = new HashMap<>();
        new RemoteCalledScan(this);
    }

    public Map<String, RemoteCalledPacket> getContext() {
        return context;
    }

    public static class RemoteCalledPacket {
        private String ip;
        private int port;
        private ProtocolTypeEnum protocol;

        public RemoteCalledPacket() {

        }

        public RemoteCalledPacket(String ip, int port, ProtocolTypeEnum protocol) {
            this.ip = ip;
            this.port = port;
            this.protocol = protocol;
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
    }

}
