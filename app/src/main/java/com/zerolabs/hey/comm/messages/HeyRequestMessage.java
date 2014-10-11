package com.zerolabs.hey.comm.messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jpedro on 11.10.14.
 */
public class HeyRequestMessage implements RequestMessage {

    // CONSTANTS

    public static String KEY_FROM = "from";
    public static String KEY_TO = "to";

    // ATTRIBUTES

    private String mFromId;
    private String mToId;

    // CONSTRUCTOR

    public HeyRequestMessage(String fromId, String toId) {
        mFromId = fromId;
        mToId = toId;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject heyJson = new JSONObject();

        heyJson.put(KEY_FROM, mFromId);
        heyJson.put(KEY_TO, mToId);

        return heyJson;
    }

}
