package com.bluelinelabs.logansquare.demo.serializetasks;

import android.content.Context;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;
import com.squareup.moshi.Moshi;

import java.io.File;

import okio.BufferedSink;
import okio.Okio;

public class MoshiFileSerializer extends Serializer {

    private final Moshi moshi;
    private final Context context;

    public MoshiFileSerializer(
            Context context,
            SerializeListener parseListener,
            ResponseAV response,
            Moshi moshi) {
        super(parseListener, response);
        this.moshi = moshi;
        this.context = context;
    }

    @Override
    protected String serialize(ResponseAV response) {
        try (BufferedSink bufferedSink = Okio.buffer(Okio.sink(new File(context.getFilesDir(), "moshifileserializer")))
         ) {
            String s = moshi.adapter(ResponseAV.class).toJson(response);
            bufferedSink.writeUtf8(s);
            return "";
        } catch (Exception e) {
            return null;
        } finally {
            System.gc();
        }
    }
}
