package org.egg.integration.erpc.transport.server.accept;

import org.egg.integration.erpc.transport.server.handle.KeyHandler;
import org.egg.integration.erpc.transport.server.handle.TransportHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TransportAcceptor implements KeyHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportAcceptor.class);

    private Selector selector;
    private ServerSocketChannel channel;

    public TransportAcceptor(ServerSocketChannel channel, Selector selector) {
        this.channel = channel;
        this.selector = selector;
    }

    @Override
    public void handle() {
        try {
            SocketChannel socketChannel = channel.accept();
            if(socketChannel != null) {
                TransportHandler handler = new TransportHandler(socketChannel, selector);
                LOGGER.info("接收到{}的连接请求，并将具体的操作交给通道{}处理", socketChannel.getRemoteAddress(), handler);
            }

        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }

}
