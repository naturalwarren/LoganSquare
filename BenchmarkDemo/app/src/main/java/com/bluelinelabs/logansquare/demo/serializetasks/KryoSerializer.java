package com.bluelinelabs.logansquare.demo.serializetasks;

import android.content.Context;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileNotFoundException;

public class KryoSerializer extends Serializer {

    private Kryo kryo;
    private Output output;

    public KryoSerializer(
            Context context,
            SerializeListener parseListener,
            ResponseAV response,
            Kryo kryo) {
        super(parseListener, response);
        this.kryo = kryo;

        try {
            output = new Output(context.openFileOutput("kryoserialize", Context.MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String serialize(ResponseAV response) {
        try {
            kryo.writeObject(output, response);
            output.flush();
            return response.toString();
        } catch (Exception e) {
            return null;
        } finally {
            System.gc();
        }
    }

}

