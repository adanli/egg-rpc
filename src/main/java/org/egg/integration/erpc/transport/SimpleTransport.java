package org.egg.integration.erpc.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTransport extends Transport {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTransport.class);

    @Override
    public void send(String serializeMessage) {
        LOGGER.info("发送消息: " + serializeMessage);
    }
}
