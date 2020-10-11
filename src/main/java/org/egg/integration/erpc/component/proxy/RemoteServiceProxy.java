package org.egg.integration.erpc.component.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteServiceProxy implements InvocationHandler {
    private Object object;

    public RemoteServiceProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        StringBuffer sb = new StringBuffer("class: %s\nmethod: %s\nargs: ");
        Object[] parameters = new Object[2 + args.length];
        parameters[0] = object;
        parameters[1] = method.getName();

        for(int i=0; i< args.length; i++) {
            if(i != args.length-1) {
                sb.append("%s, ");
            } else {
                sb.append("%s");
            }
            parameters[i+2] = args[i];
        }
        System.out.println(String.format(sb.toString(), parameters));

        return method.invoke(object, args);
    }


}
