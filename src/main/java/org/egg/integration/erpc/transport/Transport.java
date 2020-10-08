package org.egg.integration.erpc.transport;

/**
 * 通信层, 请求实际上通过这一层进行发出
 */
public abstract class Transport {
    /**
     * 发送消息
     * @param serializeMessage 经过序列化后的消息内容
     */
    abstract public void send(String serializeMessage);
}
