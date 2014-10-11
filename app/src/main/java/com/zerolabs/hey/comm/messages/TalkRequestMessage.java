package com.zerolabs.hey.comm.messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jpedro on 11.10.14.
 */
public class TalkRequestMessage implements RequestMessage {

    // CONSTANTS

    public static String KEY_FROM = "from";
    public static String KEY_TO = "to";
    public static String KEY_TALK = "text";

    // ATTRIBUTES

    private String mFromId;
    private String mToId;
    private String mTalk;

    public TalkRequestMessage(String fromId, String toId, String talk) {
        mFromId = fromId;
        mToId = toId;
        mTalk = talk;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject message = new JSONObject();

        message.put(KEY_FROM, mFromId);
        message.put(KEY_TO, mToId);
        message.put(KEY_TALK, mTalk);

        return message;
    }
}
