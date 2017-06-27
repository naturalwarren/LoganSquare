package com.bluelinelabs.logansquare.demo.parsetasks;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.demo.model.av.ResponseAV;

public class LoganSquareParser extends Parser {

    public LoganSquareParser(ParseListener parseListener, String jsonString) {
        super(parseListener, jsonString);
    }

    @Override
    protected int parse(String json) {
        try {
            ResponseAV response = LoganSquare.parse(json, ResponseAV.class);
            return response.users().size();
        } catch (Exception e) {
            return 0;
        } finally {
            System.gc();
        }
    }

}
