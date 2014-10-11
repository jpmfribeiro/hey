package com.zerolabs.hey.comm.messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jpedro on 11.10.14.
 */
public abstract class ResponseMessage {

    // CONSTANTS

    public static String KEY_SUCCESSFUL = "success";

    // ATTRIBUTES

    private boolean mWasSuccessful;

    protected JSONObject mJsonResponse;

    // CONSTRUCTORS

    public ResponseMessage(JSONObject jsonResponse) {
        mJsonResponse = jsonResponse;
    }

    // GETTERS

    public boolean wasSuccessful() throws JSONException {
        return mJsonResponse.getInt(KEY_SUCCESSFUL) == 1;
    }

}
