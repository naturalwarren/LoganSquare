package com.bluelinelabs.logansquare.demo.serializetasks;

import android.os.AsyncTask;

import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;

import java.util.concurrent.TimeUnit;

public abstract class Serializer extends AsyncTask<Void, Void, SerializeResult> {

    public interface SerializeListener {
        void onComplete(Serializer serializer, SerializeResult serializeResult);
    }

    private final SerializeListener mParseListener;
    private final ResponseAV mResponse;

    protected Serializer(SerializeListener parseListener, ResponseAV response) {
        mParseListener = parseListener;
        mResponse = response;
    }

    @Override
    protected SerializeResult doInBackground(Void... params) {
        System.gc();
        long startTime = System.nanoTime();
        serialize(mResponse);
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMicros(endTime - startTime);

        return new SerializeResult(duration, mResponse.users().size());
    }

    @Override
    protected void onPostExecute(SerializeResult parseResult) {
        mParseListener.onComplete(this, parseResult);
    }

    protected abstract String serialize(ResponseAV response);
}
