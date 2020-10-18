package org.egg.integration.erpc.serialize.packet.consumer;

import org.egg.integration.erpc.annotation.Service;
import org.egg.integration.erpc.constant.RequestQueue;
import org.egg.integration.erpc.serialize.packet.Packet;
import org.omg.CORBA.INITIALIZE;

import java.util.LinkedList;

@Service
public class ConsumerRequestQueue {
    private boolean start = true;
    private LinkedList<Packet> packets;

    public ConsumerRequestQueue() {
        packets = RequestQueue.queue();
        System.out.println("已获取");
        consumer();
    }


    private void consumer() {
        ConsumerRequest consumer = new ConsumerRequest();
        new Thread(consumer).start();
//        consumer.wakeup();
    }

    public void stop() {
        start = false;
    }

    class ConsumerRequest implements Runnable {

        @Override
        public void run() {
            System.out.println("开始消费");
            while (start) {
//                Thread.currentThread().interrupt();
                try {
                Thread.sleep(1);

                }catch (InterruptedException e) {

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
