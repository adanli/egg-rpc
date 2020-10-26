package org.egg.integration.erpc.transport;

import org.egg.integration.erpc.annotation.Service;
import org.egg.integration.erpc.constant.TemporaryConstant;
import org.egg.integration.erpc.serialize.packet.Packet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Service
public class SimpleTransport extends Transport {
//    private SocketChannel socketChannel;
    private final ByteBuffer buffer = ByteBuffer.allocate(TemporaryConstant.EGG_BUFFER_SIZE);

    public SimpleTransport() {
        /*try {
//            socketChannel = SocketChannel.open();
//            boolean connect = socketChannel.connect(new InetSocketAddress("localhost", 12200));
//            if(connect)
//                System.out.println("客户端连接到服务器: localhost:12200");

        } catch (IOException e) {
            System.err.println();
        }*/

    }

    {
        new Thread(new EggServer()).start();
    }

    @Override
    public void send(Packet packet) {


        byte[] bytes = packet.toString().getBytes();
        buffer.put(bytes);
        buffer.flip();
        try {
            SocketChannel socketChannel = SocketChannel.open();
            boolean connect = socketChannel.connect(new InetSocketAddress(packet.getDestIp(), packet.getDestPort()));
            if(connect) {
                socketChannel.write(buffer);
                System.out.println("发送" + packet);
            } else {
                System.err.println("连接失败");
            }

        } catch (IOException e) {
//            System.err.println();
            e.printStackTrace();
        } finally {
            buffer.clear();
        }
    }
}
