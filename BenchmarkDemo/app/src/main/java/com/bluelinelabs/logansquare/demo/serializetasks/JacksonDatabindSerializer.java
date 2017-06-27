package com.bluelinelabs.logansquare.demo.serializetasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;

public class JacksonDatabindSerializer extends Serializer {

    private final ObjectMapper objectMapper;

    public JacksonDatabindSerializer(SerializeListener parseListener, ResponseAV response, ObjectMapper objectMapper) {
        super(parseListener, response);
        this.objectMapper = objectMapper;
    }

    @Override
    protected String serialize(ResponseAV response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return null;
        } finally {
            System.gc();
        }
    }
}
