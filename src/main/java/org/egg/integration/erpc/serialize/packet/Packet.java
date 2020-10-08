package org.egg.integration.erpc.serialize.packet;

import org.egg.integration.erpc.serialize.body.Body;
import org.egg.integration.erpc.serialize.header.Header;

/**
 * 数据包，即数据分片
 */
public abstract class Packet {
    private Header header;
    private Body body;

    public Packet() {

    }

    public Packet(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }
}
