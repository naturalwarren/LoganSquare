package com.bluelinelabs.logansquare.demo.parsetasks;

import android.content.Context;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import okio.BufferedSource;
import okio.Okio;

public class MoshiBufferedFileParser extends Parser {
    private static volatile Random rand = new Random();

    private final Moshi moshi;
    private BufferedSource bufferedSource;
    private int randNum;

    public MoshiBufferedFileParser(
            Context context,
            ParseListener parseListener,
            String jsonString,
            Moshi moshi) {
        super(parseListener, jsonString);
        this.moshi = moshi;
        randNum = rand.nextInt(10000000) + 1;

        try {
            File file = new File(context.getFilesDir(), "moshiparser" + randNum);
            Okio.buffer(Okio.sink(file)).writeUtf8(jsonString).flush();

            bufferedSource = Okio.buffer(Okio.source(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int parse(String json) {
        try {
            return moshi.adapter(ResponseAV.class).fromJson(bufferedSource).users().size();
        } catch (Exception e) {
            return 0;
        } finally {
            System.gc();
        }
    }

}
