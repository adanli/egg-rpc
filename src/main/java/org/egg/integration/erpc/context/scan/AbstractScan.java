package org.egg.integration.erpc.context.scan;

import org.egg.integration.erpc.constant.TemporaryConstant;
import org.egg.integration.erpc.context.BeanContext;

public abstract class AbstractScan {
    protected BeanContext context;
    private final String beanScanConverage = TemporaryConstant.EGG_RPC_COMPONENT_SCAN;

    public AbstractScan(BeanContext context) {
        this.context = context;
    }

    protected void scan(String dir) {

    }

    /*{
        scan();
    }*/

}
