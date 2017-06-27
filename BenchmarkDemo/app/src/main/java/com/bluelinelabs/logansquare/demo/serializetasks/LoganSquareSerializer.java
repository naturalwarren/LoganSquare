package com.bluelinelabs.logansquare.demo.serializetasks;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;

public class LoganSquareSerializer extends Serializer {

    public LoganSquareSerializer(SerializeListener parseListener, ResponseAV response) {
        super(parseListener, response);
    }

    @Override
    protected String serialize(ResponseAV response) {
        try {
            return LoganSquare.serialize(response);
        } catch (Exception e) {
            return null;
        } finally {
            System.gc();
        }
    }
}
