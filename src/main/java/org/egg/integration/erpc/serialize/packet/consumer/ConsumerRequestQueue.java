package org.egg.integration.erpc.serialize.packet.consumer;

import org.egg.integration.erpc.annotation.Service;
import org.egg.integration.erpc.constant.RequestQueue;
import org.egg.integration.erpc.serialize.packet.Packet;
import sun.nio.ch.Interruptible;

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
        private Interruptible interruptor = null;

        @Override
        public void run() {
            while (start) {
                this.begin();
                doConsumer();
                this.end();
            }
        }

        private void doConsumer() {
            while (packets.size() > 0) {
                synchronized (RequestQueue.REQUEST_QUEUE_LOCK) {
                    Packet packet = packets.pollLast();
                    System.out.println("消费" + packet);
                }
            }
        }

        private void begin() {
            if(interruptor == null) {
                interruptor = new Interruptible() {
                    @Override
                    public void interrupt(Thread thread) {

                    }
                };
            }
            blockOn(interruptor);
            Thread me = Thread.currentThread();
            if(me.isInterrupted()) {
                interruptor.interrupt(me);
            }
        }

        private void end() {
            blockOn(null);
        }

    }

    private static void blockOn(Interruptible interrupt) {
        sun.misc.SharedSecrets.getJavaLangAccess().blockedOn(Thread.currentThread(), interrupt);
    }
}
