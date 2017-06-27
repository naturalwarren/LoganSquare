package com.bluelinelabs.logansquare.demo.serializetasks;

import android.content.Context;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.Okio;

public class MoshiSerializer extends Serializer {

    private final Moshi moshi;
    private BufferedSink bufferedSink;

    public MoshiSerializer(
            Context context,
            SerializeListener parseListener,
            ResponseAV response,
            Moshi moshi) {
        super(parseListener, response);
        this.moshi = moshi;

        try {
            bufferedSink = Okio.buffer(Okio.sink(new File(context.getFilesDir(), "moshiserializer")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String serialize(ResponseAV response) {
        try {
            moshi.adapter(ResponseAV.class).toJson(bufferedSink, response);
            return response.toString();
        } catch (Exception e) {
            return null;
        } finally {
           System.gc();
        }
    }
}
