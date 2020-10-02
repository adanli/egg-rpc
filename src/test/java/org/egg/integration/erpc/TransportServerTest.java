package org.egg.integration.erpc;


import org.egg.integration.erpc.transport.server.TransportServer;
import org.junit.Test;

import java.io.IOException;

public class TransportServerTest {

    @Test
    public void testServer() {
        Thread t1 = new Thread(new TransportServer());
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
