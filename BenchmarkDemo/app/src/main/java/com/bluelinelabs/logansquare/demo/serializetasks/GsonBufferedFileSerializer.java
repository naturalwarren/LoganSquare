package com.bluelinelabs.logansquare.demo.serializetasks;

import android.content.Context;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class GsonBufferedFileSerializer extends Serializer {

    private final Gson gson;
    private final Context context;

    public GsonBufferedFileSerializer(
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
        try (Writer writer = new OutputStreamWriter(context.openFileOutput("gsonserializer.txt", Context.MODE_PRIVATE))) {
            gson.toJson(response, writer);
            return "";
        } catch (IOException e) {
            return null;
        } finally {
            System.gc();
        }
    }

}
