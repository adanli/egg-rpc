package org.egg.integration.erpc.serialize;

public abstract class AbstractSerialize<Packet> {

    abstract public String serialize(Packet packet);

    abstract public Packet deSerialize(String str);

}
