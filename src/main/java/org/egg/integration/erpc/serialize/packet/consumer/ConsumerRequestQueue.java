package org.egg.integration.erpc.serialize.packet.consumer;

import org.egg.integration.erpc.annotation.Service;
import org.egg.integration.erpc.constant.RequestQueue;
import org.egg.integration.erpc.serialize.packet.Packet;

import java.util.LinkedList;

@Service
public class ConsumerRequestQueue {
    private boolean start = true;
    private LinkedList<Packet> packets;

    public ConsumerRequestQueue() {
        packets = RequestQueue.queue();
        consumer();
    }


    private void consumer() {
        ConsumerRequest consumer = new ConsumerRequest();
        new Thread(consumer).start();
    }

    public void stop() {
        start = false;
    }

    class ConsumerRequest implements Runnable {

        @Override
        public void run() {
            while (start) {
                Thread.currentThread().interrupt();
                while (packets.size() > 0) {
                    synchronized (RequestQueue.REQUEST_QUEUE_LOCK) {
                        Packet packet = packets.pollLast();
                        System.out.println("消费" + packet);
                    }
                }
            }
        }



    }

}
