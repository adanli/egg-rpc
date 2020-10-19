package org.egg.integration.erpc.transport;

import org.egg.integration.erpc.serialize.packet.Packet;

/**
 * 通信层, 请求实际上通过这一层进行发出
 */
public abstract class Transport {
    /**
     * 发送消息
     * @param packet 消息包
     */
    abstract public void send(Packet packet);
}
