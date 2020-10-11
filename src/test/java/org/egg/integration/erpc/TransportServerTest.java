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

    /**
     * 测试自定义注解，将标注了该注解的类下所有的方法都转换成json串
     */
    @Test
    public void testAnnotation() {

    }

}
