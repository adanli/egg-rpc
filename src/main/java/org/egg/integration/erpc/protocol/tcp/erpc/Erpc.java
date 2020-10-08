package org.egg.integration.erpc.protocol.tcp.erpc;

import org.egg.integration.erpc.protocol.tcp.ITcp;
import org.egg.integration.erpc.serialize.Serialize;
import org.egg.integration.erpc.serialize.SimpleSerialize;
import org.egg.integration.erpc.serialize.body.Body;
import org.egg.integration.erpc.serialize.body.SimpleBody;
import org.egg.integration.erpc.serialize.header.Header;
import org.egg.integration.erpc.serialize.header.SimpleHeader;
import org.egg.integration.erpc.serialize.packet.Packet;
import org.egg.integration.erpc.serialize.packet.SimplePacket;
import org.egg.integration.erpc.transport.SimpleTransport;
import org.egg.integration.erpc.transport.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义的egg-rpc，遵循tcp协议实现的规定的基本功能
 */
public class Erpc implements ITcp {
    private static final Logger LOGGER = LoggerFactory.getLogger(Erpc.class);
    private final Transport transport = new SimpleTransport();
    private final Serialize serialize = new SimpleSerialize();

    /**
     * 数据分片
     * key: long类型，一个数据包中的requestId
     * value: list类型，一个数据分片集合
     */
    private final Map<Long, List<Packet>> splitPackets = new ConcurrentHashMap<>();

    private final Packet testPacket;

    {
        testPacket = new SimplePacket();
        Header header = new SimpleHeader();
        header.setPacketCount(1);
        header.setPacketNumber(1);
        header.setRequestId(111);
        Body body = new SimpleBody();
        body.setClassName("org.egg.integration.erpc.service.DemoService");
        body.setMethod("hello");
        body.setParameters("");
        testPacket.setHeader(header);
        testPacket.setBody(body);
        header.setLength(serialize.serialize(testPacket).length());
    }

    @Override
    public void ack() {

    }

    /**
     * 此处以一个请求封装好的请求对象为例
     */
    @Override
    public void send() {
        // fixme 如果数据包太大，这里需要考虑拆分成若干个数据分片
        transport.send(serialize.serialize(testPacket));
        // fixme 超时重试这块之后考虑用FutureTask实现
    }

    @Override
    public void split() {

    }

    @Override
    public void judgeRepeat() {

    }

    @Override
    public void check() {

    }

    @Override
    public void discard() {

    }
}
