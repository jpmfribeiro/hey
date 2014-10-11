package com.zerolabs.hey.comm.messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jpedro on 11.10.14.
 */
public class RegisterResponseMessage extends ResponseMessage {

    // CONSTRUCTOR
    public RegisterResponseMessage(JSONObject jsonResponse) throws JSONException {
        super(jsonResponse);
    }

}
