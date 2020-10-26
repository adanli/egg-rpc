package org.egg.integration.erpc.context.extra;

import org.egg.integration.erpc.context.scan.annotation.RemoteReferenceScan;
import org.egg.integration.erpc.protocol.ProtocolTypeEnum;

import java.util.HashMap;
import java.util.Map;

public class RemoteReferenceContext extends ExtraContext {
    private final Map<String, Object> contextMap;
    private final Map<String, RemoteCalledPacket> packet;
    private static RemoteReferenceContext context;

    @Override
    public void mount() {
        new RemoteReferenceScan(context);
    }

    private RemoteReferenceContext() {
        contextMap = new HashMap<>();
        packet = new HashMap<>();
    }

    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    public static RemoteReferenceContext getContext() {
        if(context == null) {
            synchronized (RemoteReferenceContext.class) {
                if(context == null) {
                    context = new RemoteReferenceContext();
                }
            }
        }
        return context;
    }

    public Map<String, RemoteCalledPacket> getPacket() {
        return packet;
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
