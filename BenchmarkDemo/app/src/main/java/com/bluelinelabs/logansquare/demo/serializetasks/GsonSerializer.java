package com.bluelinelabs.logansquare.demo.serializetasks;

import android.content.Context;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class GsonSerializer extends Serializer {

    private final Gson gson;
    private Writer writer;

    public GsonSerializer(
            Context context,
            SerializeListener parseListener,
            ResponseAV response,
            Gson gson) {
        super(parseListener, response);
        this.gson = gson;

        try {
            writer = new OutputStreamWriter(context.openFileOutput("gsonserializer.txt", Context.MODE_PRIVATE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String serialize(ResponseAV response) {
        try {
            gson.toJson(response, writer);
            writer.flush();
            return response.toString();
        } catch (Exception e) {
            return null;
        } finally {
            System.gc();
        }
    }

}
