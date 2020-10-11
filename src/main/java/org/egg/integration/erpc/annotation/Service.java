package org.egg.integration.erpc.annotation;

import java.lang.annotation.*;

/**
 * 注解, 用于注入对象
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
public @interface Service {
    String value() default "";
}
