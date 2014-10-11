package com.zerolabs.hey.comm.messages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by jpedro on 11.10.14.
 */
public class UsersFromMacRequestMessage implements RequestMessage {

    // CONSTANTS
    public static String KEY_MAC_ADDRESSES = "mac_addresses";

    // ATTRIBUTES
    private List<String> mMacAddresses;

    public UsersFromMacRequestMessage(List<String> macAddresses) {
        mMacAddresses = macAddresses;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject result = new JSONObject();

        JSONArray macAddrArray = new JSONArray(mMacAddresses);

        result.put(KEY_MAC_ADDRESSES, macAddrArray);

        return result;
    }
}
