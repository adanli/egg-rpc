package org.egg.integration.erpc.serialize.packet.consumer;

import org.egg.integration.erpc.annotation.Service;
import org.egg.integration.erpc.constant.RequestQueue;
import org.egg.integration.erpc.serialize.packet.Packet;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@Service
public class ConsumerRequestQueue {
    private int state = 0;
    private LinkedList<Packet> packets;

    public ConsumerRequestQueue() {
        packets = RequestQueue.queue();
        System.out.println("已获取");
        consumer();
    }


    private void consumer() {
        new Thread(new ConsumerRequest()).start();
    }

    public void stop() {
        state = state << 1;
    }

    class ConsumerRequest implements Runnable {

        @Override
        public void run() {
            System.out.println("开始消费");
            while (state == 0) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
