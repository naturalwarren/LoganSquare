package com.bluelinelabs.logansquare.demo.serializetasks;

import android.content.Context;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

public class KryoFileSerializer extends Serializer {

    private final Context context;
    private Kryo kryo;

    public KryoFileSerializer(
            Context context,
            SerializeListener parseListener,
            ResponseAV response,
            Kryo kryo) {
        super(parseListener, response);
        this.kryo = kryo;
        this.context = context;

    }

    @Override
    protected String serialize(ResponseAV response) {
        try (Output output = new Output(context.openFileOutput("kryofileserialize", Context.MODE_PRIVATE))) {
            kryo.writeObject(output, response);

            byte[] bytes = output.toBytes();
            kryo.writeObject(output, bytes);
            return "";
        } catch (Exception e) {
            return null;
        } finally {
            System.gc();
        }
    }

}

