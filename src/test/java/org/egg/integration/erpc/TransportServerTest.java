package org.egg.integration.erpc;


import org.egg.integration.erpc.component.proxy.RemoteCallProxyFactory;
import org.egg.integration.erpc.context.EggContext;
import org.egg.integration.erpc.generator.SnowFlakeWorker;
import org.egg.integration.erpc.protocol.tcp.ITcp;
import org.egg.integration.erpc.protocol.tcp.erpc.Erpc;
import org.egg.integration.erpc.service.DemoService;
import org.egg.integration.erpc.service.impl.DemoReferenceServiceImpl;
import org.junit.Test;

import java.nio.charset.Charset;

public class TransportServerTest {

    @Test
    public void testAll() throws Exception{
        testInitContext();
        testAnnotation();
        testReference();
//        Thread.currentThread().join();
    }

    @Test
    public void testReference() {
        DemoReferenceServiceImpl demoReferenceService = (DemoReferenceServiceImpl) RemoteCallProxyFactory.getBean("demoReferenceService");
        demoReferenceService.hello();
    }

    @Test
    public void testInitContext() {
//        new EggContext();
        EggContext.show();
    }

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
        DemoService demoService = (DemoService) RemoteCallProxyFactory.getBean("demoService");
        demoService.hello(  "aaa");

        /*try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        demoService.hi("bbb");*/
    }

    /**
     * 将代理中的参数，结合注解中的基本信息，封装成Packet
     */
    @Test
    public void testPackageRequestIntoPacket() {

    }

    @Test
    public void testLength() {
        testInitContext();
        System.out.println(" ".getBytes().length); // 1
        System.out.println("\n".getBytes().length); // 1
        SnowFlakeWorker snowFlakeWorker = (SnowFlakeWorker) RemoteCallProxyFactory.getBean("snowFlakeWorker");

        Long seqId = snowFlakeWorker.nextId();
        System.out.println(String.valueOf(seqId).getBytes(Charset.defaultCharset()).length); // 18


    }

}
