package com.zerolabs.hey.comm.messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jpedro on 11.10.14.
 */
public interface RequestMessage {

    public JSONObject toJson() throws JSONException;

}
