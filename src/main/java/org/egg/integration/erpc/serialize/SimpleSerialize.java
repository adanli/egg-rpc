package org.egg.integration.erpc.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSerialize implements Serialize {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSerialize.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String serialize(Object object) {
        try {
            return MAPPER.writeValueAsString(object);

        } catch (JsonProcessingException e) {
            LOGGER.error("", e);
        }
        return null;
    }

    @Override
    public <T> T deSerialize(String json, Class<T> clazz) {
        return MAPPER.convertValue(json, clazz);
    }
}
