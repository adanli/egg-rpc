package org.egg.integration.erpc.component.scan;

public abstract class AbstractAnnotationScan {

    public AbstractAnnotationScan() {
        scan();
    }

    protected abstract void scan();
}
