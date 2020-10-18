package org.egg.integration.erpc.constant;


import org.egg.integration.erpc.serialize.packet.Packet;

import java.util.LinkedList;
import java.util.List;

public class RequestQueue {
    private transient static final List<Packet> queue = new LinkedList<>();
    public static final Object REQUEST_QUEUE_LOCK = new Object();

    public static void attach(Packet packet) {
        synchronized (REQUEST_QUEUE_LOCK) {
            queue.add(packet);
        }
    }

    public static LinkedList<Packet> queue() {
        return (LinkedList<Packet>) queue;
    }

}
