package org.egg.integration.erpc;


import org.egg.integration.erpc.component.proxy.RemoteCallProxyFactory;
import org.egg.integration.erpc.component.scan.AnnotationBeanAnnotationScan;
import org.egg.integration.erpc.context.EggContext;
import org.egg.integration.erpc.protocol.tcp.ITcp;
import org.egg.integration.erpc.protocol.tcp.erpc.Erpc;
import org.egg.integration.erpc.service.DemoService;
import org.junit.Test;

public class TransportServerTest {

    @Test
    public void testAll() {
//        testInitScanToContext();
//        testAnnotation();
        testInitContext();
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
        if(demoService != null) {
            demoService.hello(  "aaa");
            System.out.println("---------------------------");
            demoService.hi("name");
        }
    }

    /**
     * 将带有@RemoteService注解的对象在项目启动的时候加入到context中
     */
    @Test
    public void testInitScanToContext() {
        new AnnotationBeanAnnotationScan();
    }

    /**
     * 将代理中的参数，结合注解中的基本信息，封装成Packet
     */
    @Test
    public void testPackageRequestIntoPacket() {

    }

}
