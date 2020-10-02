package org.egg.integration.erpc.serialize.packet;

import org.egg.integration.erpc.protocol.ProtocolEnum;

import java.awt.color.ProfileDataException;

/**
 * 请求包, 定义一个rpc请求过程中的包内容, 包括
 * 请求头: 请求长度、协议类型、请求ID、一次请求中涉及到的包数量、当前包位于请求中的位置
 * 请求体: 类信息、方法信息、参数信息
 */
public abstract class AbstractPacket {

    /**
     * Packet包中的头部类，用户标志当前包的一些独有信息
     */
    static final class Header {
        /**
         * length: 一次请求所有内容长度
         * protocolType: 请求的协议类型，目前只支持ERPC
         * requestId: 请求的id，自增唯一
         * packetCount: 一次请求中的packet数量, 默认是1
         * packetNumber: 当前packet在一次请求涉及到的packet包序列中的位置, 默认是0
         */
        private final int length;
        private final ProtocolEnum protocolType;
        private final long requestId;
        private int packetCount = 1;
        private int packetNumber = 0;

        public Header(int length, ProtocolEnum protocolType, long requestId) {
            this.length = length;
            this.protocolType = protocolType;
            this.requestId = requestId;
        }

        public Header(int length, ProtocolEnum protocolType, long requestId, int packetCount, int packetNumber) {
            this.length = length;
            this.protocolType = protocolType;
            this.requestId = requestId;
            this.packetCount = packetCount;
            this.packetNumber = packetNumber;
        }

    }

    public static Header providerHeader(int length, ProtocolEnum protocolEnum, long requestId, int packetCount, int packetNumber) {
        return new Header(length, protocolEnum, requestId, packetCount, packetCount);
    }

    /**
     * Packet包中的参数体，包括远程请求的类名、方法名、参数名
     */
    static final class Body {
        private final String className;
        private final String method;
        private final String parameters;

        public Body(String className, String method, String parameters) {
            this.className = className;
            this.method = method;
            this.parameters = parameters;
        }

    }

    public static Body providerBody(String className, String method, String parameters) {
        return new Body(className, method, parameters);
    }

}
