package org.egg.integration.erpc.serialize.packet.consumer;

import org.egg.integration.erpc.annotation.Service;
import org.egg.integration.erpc.component.proxy.RemoteCallProxyFactory;
import org.egg.integration.erpc.constant.RequestQueue;
import org.egg.integration.erpc.serialize.packet.Packet;
import org.egg.integration.erpc.transport.Transport;
import sun.nio.ch.Interruptible;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
public class ConsumerRequestQueue {
    private boolean start = true;
    public static LinkedList<Packet> packets;

    public static Set<Thread> set = new HashSet<>();

    public ConsumerRequestQueue() {
        packets = RequestQueue.queue();
        consumer();
    }

    private void consumer() {
        Transport transport = (Transport) RemoteCallProxyFactory.getBean("simpleTransport");
        new Thread(new ConsumerRequest(transport)).start();
    }

    public void stop() {
        start = false;
    }

    class ConsumerRequest implements Runnable {
        private Interruptible interruptor = null;
        private Packet packet;
        private Transport transport;

        public ConsumerRequest(Transport transport) {
            this.transport = transport;
        }

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
                    packet = packets.pollLast();
//                    System.out.println("消费" + packet);
                    transport.send(packet);
                    packet = null;
                }
            }
        }

        private void begin() {
            if(interruptor == null) {
                interruptor = new Interruptible() {
                    @Override
                    public void interrupt(Thread thread) {
                        // 如果出现问题，将当前packet放回
                        reimportPacket(packet);
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

        private void reimportPacket(Packet packet) {
            if(packet != null) {
                RequestQueue.attach(packet);
            }
        }

    }

    private static void blockOn(Interruptible interrupt) {
        sun.misc.SharedSecrets.getJavaLangAccess().blockedOn(Thread.currentThread(), interrupt);
    }

}
