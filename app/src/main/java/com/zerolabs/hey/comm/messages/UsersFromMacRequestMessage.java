package com.zerolabs.hey.comm.messages;

import com.zerolabs.hey.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by jpedro on 11.10.14.
 */
public class UsersFromMacRequestMessage implements RequestMessage {

    // CONSTANTS
    public static String KEY_MAC_ADDRESSES = "macaddresses";

    public static String KEY_USERID = User.JSONRep.KEY_USERID;

    // ATTRIBUTES
    private List<String> mMacAddresses;
    private String mUserId;

    public UsersFromMacRequestMessage(List<String> macAddresses, String userId) {
        mMacAddresses = macAddresses;
        mUserId = userId;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject result = new JSONObject();

        result.put(KEY_USERID, mUserId);

        JSONArray macAddrArray = new JSONArray(mMacAddresses);

        result.put(KEY_MAC_ADDRESSES, macAddrArray);

        return result;
    }
}
