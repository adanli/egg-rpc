package org.egg.integration.erpc;


import org.egg.integration.erpc.protocol.tcp.ITcp;
import org.egg.integration.erpc.protocol.tcp.erpc.Erpc;
import org.junit.Test;

public class TransportServerTest {

    @Test
    public void request() {
        ITcp rpc = new Erpc();
        rpc.send();

    }

}
