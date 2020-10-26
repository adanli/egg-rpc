package org.egg.integration.erpc.annotation;

import org.egg.integration.erpc.constant.TemporaryConstant;
import org.egg.integration.erpc.protocol.ProtocolTypeEnum;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RemoteReference {
    String ip() default "";
    int port() default TemporaryConstant.EGG_RPC_PORT;
    ProtocolTypeEnum protocol() default ProtocolTypeEnum.ERPC;
}
