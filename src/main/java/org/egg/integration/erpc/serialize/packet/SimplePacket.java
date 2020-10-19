package org.egg.integration.erpc.serialize.packet;

import org.egg.integration.erpc.component.proxy.RemoteCallProxyFactory;
import org.egg.integration.erpc.constant.RequestQueue;
import org.egg.integration.erpc.generator.SnowFlakeWorker;
import org.egg.integration.erpc.serialize.Serialize;
import org.egg.integration.erpc.serialize.SimpleSerialize;

public class SimplePacket extends Packet {
    private final Serialize serialize = new SimpleSerialize();

    /**
     * 填充summary的内容, 并序列化打印
     */
    @Override
    public void execute() {
        StringBuilder sb = new StringBuilder();
        SnowFlakeWorker snowFlakeWorker = (SnowFlakeWorker) RemoteCallProxyFactory.getBean("snowFlakeWorker");
        long seqId = snowFlakeWorker.nextId();
        sb.append(seqId).append('\n').append(1).append('\n').append(1).append('\n');
        setSummary(sb.toString());
        String content = serialize.serialize(super.content);
        sb.append(content);

        RequestQueue.attach(this);
//        System.out.println("已添加, size: " + RequestQueue.queue().size());
        /*Thread t = ConsumerRequestQueue.set.iterator().next();
        System.out.println("线程状态: " + t.getState().name());
        System.out.println("消费者packets数量: " + ConsumerRequestQueue.packets.size());*/

    }

    @Override
    public String toString() {
        return "SimplePacket{" +
                "sourceIp='" + sourceIp + '\'' +
                ", destIp='" + destIp + '\'' +
                ", destPort=" + destPort +
                ", protocol=" + protocol +
                ", summary='" + summary + '\'' +
                ", content=" + content +
                '}';
    }
}
