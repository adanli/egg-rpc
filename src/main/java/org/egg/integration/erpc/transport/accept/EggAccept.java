package org.egg.integration.erpc.transport.accept;

import org.egg.integration.erpc.transport.handler.EggHandler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 接收accept请求，并绑定到read事件
 */
public class EggAccept implements Runnable{
    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;

    public EggAccept(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);
            SelectionKey key = client.register(selector, SelectionKey.OP_READ);
            key.attach(new EggHandler(client, key));

        } catch (IOException e) {
            System.err.println();
        }
    }
}
