package org.egg.integration.erpc.context.scan;

import org.egg.integration.erpc.context.BeanContext;

public abstract class AbstractScan {
    protected BeanContext context;

    public AbstractScan(BeanContext context) {
        this.context = context;
    }

    protected abstract void scan();

    /*{
        scan();
    }*/

}
