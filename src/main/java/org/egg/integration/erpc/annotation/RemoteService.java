package org.egg.integration.erpc.annotation;

import org.egg.integration.erpc.protocol.ProtocolTypeEnum;

import java.lang.annotation.*;

/**
 * RPC远程调用注解
 * 该注解作用于某个类，该类下所有的方法都会被拦截，并打印当前类、执行的方法和传递的参数
 * 1. 对端ip, 必填
 * 2. 对端端口, 必填
 * 3. 使用的协议
 * 4. 当前注解修饰的类的name
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
public @interface RemoteService {
    String ip() default "";
    int port() default 8080;
    ProtocolTypeEnum protocol() default ProtocolTypeEnum.ERPC;
    String name() default "";
}
