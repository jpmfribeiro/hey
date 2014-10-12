package com.zerolabs.hey.comm.messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jpedro on 11.10.14.
 */
public class TalkRequestMessage implements RequestMessage {

    // CONSTANTS

    public static String KEY_SENDER = "sender";
    public static String KEY_RECEIVER = "receiver";
    public static String KEY_TALK = "text";

    // ATTRIBUTES

    private String mSenderId;
    private String mReceiverId;
    private String mTalk;

    public TalkRequestMessage(String senderId, String receiverId, String talk) {
        mSenderId = senderId;
        mReceiverId = receiverId;
        mTalk = talk;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject message = new JSONObject();

        message.put(KEY_SENDER, mSenderId);
        message.put(KEY_RECEIVER, mReceiverId);
        message.put(KEY_TALK, mTalk);

        return message;
    }
}
