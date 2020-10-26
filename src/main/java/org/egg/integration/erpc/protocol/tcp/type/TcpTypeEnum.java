package org.egg.integration.erpc.protocol.tcp.type;

/**
 * 消息类型
 */
public enum TcpTypeEnum {
    /**
     * 普通消息, 用于普通的rpc调用时候发送的
     */
    NORMAL,

    /**
     * 回执类型的消息, 当接收端收到发送端的消息后，向发送端发送的回执
     */
    ACK,

    /**
     * 请求信息，用于初始化的时候获取被@RemoteReference标注的对象
     */
    CONNECT,
}
