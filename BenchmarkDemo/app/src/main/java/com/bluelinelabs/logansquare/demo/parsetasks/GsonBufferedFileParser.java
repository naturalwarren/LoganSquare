package com.bluelinelabs.logansquare.demo.parsetasks;

import android.content.Context;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Random;


public class GsonBufferedFileParser extends Parser {
    private static volatile Random rand = new Random();

    private final Gson gson;
    private Reader reader;
    private int randNum;

    public GsonBufferedFileParser(
            Context context,
            ParseListener parseListener,
            String jsonString,
            Gson gson) {
        super(parseListener, jsonString);
        this.gson = gson;
        randNum = rand.nextInt(10000000) + 1;

        try {
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("gsonparser" + randNum,
                    Context.MODE_PRIVATE));
            writer.write(jsonString);
            writer.flush();

            reader = new InputStreamReader(context.openFileInput("gsonparser" + randNum));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int parse(String json) {
        try {
            ResponseAV response = gson.fromJson(reader, ResponseAV.class);
            return response.users().size();
        } catch (Exception e) {
            return 0;
        } finally {
            System.gc();
        }
    }

}
