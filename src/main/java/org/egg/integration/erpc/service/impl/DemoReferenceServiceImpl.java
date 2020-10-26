package org.egg.integration.erpc.service.impl;

import org.egg.integration.erpc.annotation.RemoteReference;
import org.egg.integration.erpc.annotation.Service;
import org.egg.integration.erpc.service.DemoService;

@Service
public class DemoReferenceServiceImpl {
    @RemoteReference
    public DemoService demoService;

    public void hello() {
        demoService.hello("ccc");
    }


}
