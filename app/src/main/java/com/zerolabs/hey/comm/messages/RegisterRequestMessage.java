package com.zerolabs.hey.comm.messages;

import com.zerolabs.hey.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jpedro on 11.10.14.
 */
public class RegisterRequestMessage implements RequestMessage {

    // ATTRIBUTES

    private User mUser;

    public RegisterRequestMessage(User user) {
        mUser = user;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return mUser.toJson();
    }
}
