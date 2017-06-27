package com.bluelinelabs.logansquare.demo.serializetasks;

import android.content.Context;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class GsonFileSerializer extends Serializer {

    private final Gson gson;
    private final Context context;

    public GsonFileSerializer (
            Context context,
            SerializeListener parseListener,
            ResponseAV response,
            Gson gson)  {
        super(parseListener, response);
        this.gson = gson;
        this.context = context;
    }

    @Override
    protected String serialize(ResponseAV response) {
        try (Writer writer = new OutputStreamWriter(context.openFileOutput("gsonfileserializer.txt", Context
        .MODE_PRIVATE))) {
            String jsonString = gson.toJson(response);
            writer.write(jsonString);
            return "";
        } catch (IOException e) {
            return null;
        } finally {
            System.gc();
        }
    }
}
