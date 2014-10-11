package com.zerolabs.hey.comm.messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jpedro on 11.10.14.
 */
public class HeyRequestMessage implements RequestMessage {

    // CONSTANTS

    public static String KEY_SENDER = "sender";
    public static String KEY_RECEIVER = "receiver";

    // ATTRIBUTES

    private String mSenderId;
    private String mReceiverId;

    // CONSTRUCTOR

    public HeyRequestMessage(String senderId, String receiverId) {
        mSenderId = senderId;
        mReceiverId = receiverId;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject heyJson = new JSONObject();

        heyJson.put(KEY_SENDER, mSenderId);
        heyJson.put(KEY_RECEIVER, mReceiverId);

        return heyJson;
    }

}
