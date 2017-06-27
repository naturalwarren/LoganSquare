package com.bluelinelabs.logansquare.demo.parsetasks;

import android.content.Context;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.squareup.moshi.Moshi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import okio.BufferedSource;
import okio.Okio;

public class KryoParser extends Parser {
    private static Random rand = new Random();

    private final Kryo kryo;
    private byte[] bytes;
    private ResponseAV response;
    private Output output;
    private int randNum;

    public KryoParser(
            Context context,
            ParseListener parseListener,
            String jsonString,
            Kryo kryo,
            Moshi moshi) throws IOException {
        super(parseListener, jsonString);
        this.kryo = kryo;
        randNum =  rand.nextInt(10000000) + 1;

        response = moshi
                .adapter(ResponseAV.class)
                .fromJson(jsonString);
        try {
            output = new Output(context.openFileOutput("kryoparser" + randNum, Context.MODE_PRIVATE));
            kryo.writeObject(output, response);
            output.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FileInputStream f = context.openFileInput("kryoparser" + randNum);
        BufferedSource bufferedSource = Okio.buffer(Okio.source(f));
        bytes = bufferedSource.readByteArray();
    }


    @Override
    protected int parse(String json) {
        try {
            Class<ResponseAV> responseClazz = getAutoValueClass(ResponseAV.class);
            ResponseAV response = kryo.readObject(new Input(bytes), responseClazz);
            return response.users().size();
        } catch (Exception e) {
            return 0;
        } finally {
            System.gc();
        }
    }
    private static <T> Class<T> getAutoValueClass(Class<T> clazz) throws ClassNotFoundException {
        String pkgName = clazz.getPackage().getName();
        String className = pkgName + ".AutoValue_" + clazz.getSimpleName();
        return (Class<T>) Class.forName(className);
    }
}