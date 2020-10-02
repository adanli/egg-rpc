package org.egg.integration.erpc.transport.server;

import org.egg.integration.erpc.transport.server.accept.TransportAcceptor;
import org.egg.integration.erpc.transport.server.ex.HandlerKeyNotExistException;
import org.egg.integration.erpc.transport.server.handle.KeyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * 这里用于接收外部的请求
 * 目前使用最简单的方式，后续改成多Reactor多线程的方式处理
 */
public class TransportServer implements Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportServer.class);
    private boolean start = true;

//    @Value("${erpc.transport.port: 12200}")
    // FIXME 先写死，之后写个配置文件解析器，处理配置
    private int port = 12200;
//    @Value("${erpc.transport.select.interval: 0}")
    // FIXME 先写死，之后写个配置文件解析器，处理配置
    private long selectorInterval = 0;

    private ServerSocketChannel channel;
    private Selector selector;

    public TransportServer() {
        if(port <= 0) {
            throw new RuntimeException("端口不能小于0");
        }
        try {
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(port));

            selector = Selector.open();
            SelectionKey key = channel.register(selector, SelectionKey.OP_ACCEPT);
            key.attach(new TransportAcceptor(channel, selector));
            LOGGER.info("绑定端口{}成功", port);

        } catch (IOException e) {
            LOGGER.error("创建server失败", e);
        }
    }


    @Override
    public void run() {
        while (start) {
            try {
                if(selector.select(selectorInterval) == 0) continue;
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if(key.isValid()) {
                        dispatch(key);
                    }

                }

            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    private void dispatch(SelectionKey key) {
        KeyHandler handler = (KeyHandler) key.attachment();
        if(handler != null) {
            LOGGER.info("分发key{}", key);
            try {
                handler.handle();

            } catch (HandlerKeyNotExistException e) {
                LOGGER.error("", e);
            }
        }
    }

    public void stop() {
        if(start) {
            start = false;
        }
    }

}
