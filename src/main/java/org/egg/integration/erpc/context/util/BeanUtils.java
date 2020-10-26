package org.egg.integration.erpc.context.util;

public class BeanUtils {
    public static Object create(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
