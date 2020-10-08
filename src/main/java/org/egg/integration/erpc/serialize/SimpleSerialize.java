package org.egg.integration.erpc.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egg.integration.erpc.serialize.packet.Packet;

public class SimpleSerialize implements Serialize {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String serialize(Object object) {
        return MAPPER.writeValueAsString(object);
    }

    @Override
    public <T> T deSerialize(String json, Class<T> clazz) {
        return MAPPER.convertValue(json, clazz);
    }
}
