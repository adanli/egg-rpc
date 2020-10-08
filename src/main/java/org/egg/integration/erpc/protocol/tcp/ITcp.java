package org.egg.integration.erpc.protocol.tcp;

import org.egg.integration.erpc.protocol.Protocol;

/**
 * 自定义的tcp接口，用于定义tcp协议规定的基础功能
 */
public interface ITcp extends Protocol {
    /**
     * 接收端在收到发送端的数据分片后，给发送端返回的确认
     */
    void ack();

    /**
     * 发送端向接收端发送数据包，如果超出规定时间，接收端没有返回确认响应，发送端执行重新发送的操作
     */
    void send();

    /**
     * 将数据包切分成数据分片
     * 当需要传输的请求数据超过一个缓冲区的大小时，将数据包切分成数据分片，并给每个数据分片编写序列号
     */
    void split();

    /**
     * 接收端判断数据分片是否重复，如果重复，则把当前的数据分片丢弃
     */
    void judgeRepeat();

    /**
     * 接收端检查数据分片和数据流首部中的数据大小是否匹配，如果不匹配，将当前数据包丢去，等待发送端重新发送该数据分片
     */
    void check();

    /**
     * 丢弃当前的数据分片
     */
    void discard();

}
