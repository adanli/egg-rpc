package org.egg.integration.erpc.transport;

import org.egg.integration.erpc.constant.TemporaryConstant;
import org.egg.integration.erpc.transport.accept.EggAccept;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * 服务端
 */
//@Service
public class EggServer implements Runnable{

    private final int port = TemporaryConstant.EGG_RPC_PORT;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private boolean start = true;

    public EggServer() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            key.attach(new EggAccept(serverSocketChannel, selector));
            System.out.println("绑定端口到" + port);

        } catch (IOException e) {
            System.err.println();
        }
    }

    @Override
    public void run() {
        try {
            while (start) {
                if(selector.select() == 0) continue;
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    if(key.isValid()) {
                        handleKey(key);
                    }
                }

            }
        } catch (IOException e) {
            System.err.println();
        }
    }

    private void handleKey(SelectionKey key) {
        Runnable runnable = (Runnable) key.attachment();
        if(runnable != null) {
            runnable.run();
        }
    }

    public void stop() {
        start = false;
    }

}
