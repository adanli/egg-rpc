package org.egg.integration.erpc.context.util;

public class BeanNameUtils {
    /**
     * 将类的全路径转换成接口的形式名称，如DemoServiceImpl -> demoService
     * @param className 类的全路径
     * @return beanName
     */
    public static String beautifyBeanName(String className) {
        int startPosition = className.lastIndexOf('.');
        int endPosition = className.lastIndexOf("Impl");
        if(endPosition > -1) {
            return ((char)(className.charAt(startPosition+1)+32)) + className.substring(startPosition+2, endPosition);
        }
        return className;
    }
}
