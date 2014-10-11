package com.zerolabs.hey.comm.messages;

import com.zerolabs.hey.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpedro on 11.10.14.
 */
public class UsersFromMacResponseMessage extends ResponseMessage {

    // CONSTANTS
    public static String KEY_USERS = "users";

    public UsersFromMacResponseMessage(JSONObject jsonResponse) {
        super(jsonResponse);
    }

    public List<User> getUsers() throws JSONException {
        JSONArray usersArray = mJsonResponse.getJSONArray(KEY_USERS);

        List<User> users = new ArrayList<User>();

        for(int i = 0; i < usersArray.length(); i++) {
            users.add(User.fromJson(usersArray.getJSONObject(i)));
        }

        return users;
    }

}

