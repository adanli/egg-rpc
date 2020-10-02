package org.egg.integration.erpc.protocol;

/**
 * 定义erpc相关协议，包括协议检测
 */
public class ErpcProtocol {
    private static final String ERPC_PROTOCOL = "erpc://";

    public boolean isErpc(String uri) {
        if(uri == null) {
            return false;
        }
        return uri.contains(ERPC_PROTOCOL);
    }


}
