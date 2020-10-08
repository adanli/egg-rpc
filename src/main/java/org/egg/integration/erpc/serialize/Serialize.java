package org.egg.integration.erpc.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egg.integration.erpc.serialize.packet.Packet;

/**
 * 序列化方法
 */
public interface Serialize {
    /**
     * 将数据包序列化成json串
     * @param packet 数据包
     * @return json串
     */
    String serialize(Object packet);

    /**
     * 将json串反序列化成数据包
     * @param json json字符串
     * @return 数据包
     */
    <T> T deSerialize(String json, Class<T> clazz);

}
