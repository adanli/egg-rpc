package org.egg.integration.erpc.serialize.header;

import org.egg.integration.erpc.protocol.tcp.type.TcpTypeEnum;

/**
 * 自定义数据包的请求头抽象类
 */
public abstract class Header {
    /**
     * 当前数据包的长度
     */
    protected int length;
    /**
     * 请求编号，具备唯一性
     */
    protected long requestId;
    /**
     * 当前数据包被拆分成数据分片的数量
     */
    protected int packetCount;
    /**
     * 当前数据分片在数据包中的位置
     */
    protected int packetNumber;

    /**
     * 消息类型
     */
    protected TcpTypeEnum type;

    public Header() {

    }

    public Header(int length, long requestId, int packetCount, int packetNumber) {
        this.length = length;
        this.requestId = requestId;
        this.packetCount = packetCount;
        this.packetNumber = packetNumber;
    }

    public TcpTypeEnum getType() {
        return type;
    }

    public void setType(TcpTypeEnum type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getPacketCount() {
        return packetCount;
    }

    public void setPacketCount(int packetCount) {
        this.packetCount = packetCount;
    }

    public int getPacketNumber() {
        return packetNumber;
    }

    public void setPacketNumber(int packetNumber) {
        this.packetNumber = packetNumber;
    }
}
