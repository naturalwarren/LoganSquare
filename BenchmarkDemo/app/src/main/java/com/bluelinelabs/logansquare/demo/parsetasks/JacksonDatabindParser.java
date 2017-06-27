package com.bluelinelabs.logansquare.demo.parsetasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;

public class JacksonDatabindParser extends Parser {

    private final ObjectMapper objectMapper;

    public JacksonDatabindParser(ParseListener parseListener, String jsonString, ObjectMapper objectMapper) {
        super(parseListener, jsonString);
        this.objectMapper = objectMapper;
    }

    @Override
    protected int parse(String json) {
        try {
            ResponseAV response = objectMapper.readValue(json, ResponseAV.class);
            return response.users().size();
        } catch (Exception e) {
            return 0;
        } finally {
            System.gc();
        }
    }

}
