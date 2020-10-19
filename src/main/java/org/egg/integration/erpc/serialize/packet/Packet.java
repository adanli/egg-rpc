package org.egg.integration.erpc.serialize.packet;

import org.egg.integration.erpc.protocol.ProtocolTypeEnum;

import java.util.Arrays;

/**
 * 只包含summary和content两部分， 其中
 * summary是一串字符串，以\n分隔，包含seqId, recentCount, totalCount, 预留21个字节
 * content包含className, methodName和parameters
 * 另外
 * 包含远程调用的ip、port、protocol信息
 */
public abstract class Packet {
    protected String sourceIp;
    private int sourcePort;
    protected String destIp;

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getDestIp() {
        return destIp;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }

    public int getDestPort() {
        return destPort;
    }

    public void setDestPort(int destPort) {
        this.destPort = destPort;
    }

    protected int destPort;
    protected ProtocolTypeEnum protocol;
    protected String summary;
    protected Content content;


    public ProtocolTypeEnum getProtocol() {
        return protocol;
    }

    public String getSummary() {
        return summary;
    }

    public Content getContent() {
        return content;
    }

    public void setProtocol(ProtocolTypeEnum protocol) {
        this.protocol = protocol;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public static class Content {
        private String className;
        private String methodName;

        public String getClassName() {
            return className;
        }

        public String getMethodName() {
            return methodName;
        }

        public Object[] getParameters() {
            return parameters;
        }

        private Object[] parameters;

        public void setClassName(String className) {
            this.className = className;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public void setParameters(Object[] parameters) {
            this.parameters = parameters;
        }

        @Override
        public String toString() {
            return "Content{" +
                    "className='" + className + '\'' +
                    ", methodName='" + methodName + '\'' +
                    ", parameters=" + Arrays.toString(parameters) +
                    '}';
        }
    }

    public abstract void execute();

}
