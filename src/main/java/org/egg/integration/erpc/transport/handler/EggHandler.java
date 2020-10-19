package org.egg.integration.erpc.transport.handler;

import org.egg.integration.erpc.constant.TemporaryConstant;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 处理器，假设目前只处理read的操作
 */
public class EggHandler implements Runnable{
    private SocketChannel client;
    private final ByteBuffer buffer = ByteBuffer.allocate(TemporaryConstant.EGG_BUFFER_SIZE);
    private final SelectionKey key;

    public EggHandler(SocketChannel client, SelectionKey key) {
        this.client = client;
        this.key = key;
    }

    @Override
    public void run() {
        if(key.isReadable()) {
            this.read() ;
        } else if(key.isWritable()) {
            this.write();
        }
    }

    private void read() {
        try {
            int read = client.read(buffer);
            if(read < 0) {
                key.cancel();
                client.close();
                return;
            }

            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            System.out.println("收到" + new String(bytes, Charset.defaultCharset()));
            buffer.clear();

        } catch (IOException e) {
            System.err.println();
        }

    }

    private void write() {

    }

}
