package org.egg.integration.erpc.service.impl;

import org.egg.integration.erpc.annotation.RemoteService;
import org.egg.integration.erpc.service.DemoService;

@RemoteService(ip = "localhost")
public class DemoServiceImpl implements DemoService {
    @Override
    public void hello(String name) {
        System.out.println("hello: " + name);
    }

    @Override
    public void hi(String name) {
        System.out.println("hi: " + name);
    }
}
