package org.egg.integration.erpc.protocol;

/**
 * 协议类型
 */
public enum ProtocolTypeEnum {
    /**
     * ERPC, 使用erpc框架的默认调度协议
     */
    ERPC,
    /**
     * http, restful协议
     */
    HTTP,
    /**
     * sofaapi
     */
    SOFABOLT,
}
