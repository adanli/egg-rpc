package org.egg.integration.erpc.transport.server.handle;

import org.egg.integration.erpc.transport.server.ex.HandlerKeyNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class TransportHandler implements KeyHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportHandler.class);
//    @Value("${erpc.transport.buffer.size: 1024}")
    // FIXME 先写死，之后写个配置文件解析器，处理配置
    private int bufferSize = 1024;

    private SocketChannel channel;
    private Selector selector;
    private SelectionKey key;
    private ByteBuffer buffer;
    private SocketAddress remoteAddress;

    public TransportHandler(SocketChannel channel, Selector selector) {
        this.channel = channel;
        this.selector = selector;
        if(bufferSize <= 0) {
            bufferSize = 1024;
        }
        try {
            this.remoteAddress = channel.getRemoteAddress();
        } catch (IOException e) {
            LOGGER.error("", e);
        }
        init();
    }

    /**
     * 初始化当前处理器，初始化channel等信息
     */
    private void init() {
        try {
            channel.configureBlocking(false);
            key = channel.register(selector, SelectionKey.OP_READ);
            key.attach(this);

        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public void handle() {
        if(key.isReadable()) {
            read();
        } else if(key.isWritable()) {
            write();
        } else {
            try {
                throw new HandlerKeyNotExistException();
            } catch (HandlerKeyNotExistException e) {
                LOGGER.error("", e);
            }
        }
    }

    private void read() {
        buffer = ByteBuffer.allocate(bufferSize);
        try {
            int step = channel.read(buffer);
            if(step < 0) {
                key.cancel();
                channel.close();
                LOGGER.info("远程机器{}不再连接，关闭该连接{}", remoteAddress, channel);
            } else {
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String message = new String(bytes, Charset.defaultCharset());
                LOGGER.info("receive message: {}", message);
                buffer.clear();
            }


        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }

    private void write() {

    }

    @Override
    public String toString() {
        return "TransportHandler{" +
                "channel=" + channel +
                ", selector=" + selector +
                '}';
    }
}
