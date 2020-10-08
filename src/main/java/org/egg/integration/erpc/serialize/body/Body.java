package org.egg.integration.erpc.serialize.body;

/**
 * 数据包的实际内容
 */
public abstract class Body {
    /**
     * rpc调用的类名, 这里指当前这个类的绝对路径
     */
    private String className;
    /**
     * rpc调用的方法名
     */
    private String method;

    /**
     * rpc调用中涉及到的参数
     * 将真实的参数通过jackson序列化得到
     */
    private String parameters;

    public Body(){

    }

    public Body(String className, String method) {
        this.className = className;
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
