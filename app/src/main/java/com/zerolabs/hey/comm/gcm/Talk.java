package com.zerolabs.hey.comm.gcm;

import android.os.Bundle;
import android.util.Log;

import com.zerolabs.hey.helpers.FormatHelper;
import com.zerolabs.hey.model.User;

import org.json.JSONObject;

/**
 * Created by jpedro on 12.10.14.
 */
public class Talk {

    public static String LOG_TAG = Talk.class.getSimpleName();

    // CONSTANTS

    public static String KEY_IS_HEY = "ishey";  // will be 0 (false) if it is a talk
    public static String KEY_SENDER = "sender";
    public static String KEY_TEXT = "text";

    public static String KEY_USERNAME = User.JSONRep.KEY_USERNAME;
    public static String KEY_USERID = User.JSONRep.KEY_USERID;

    public static String KEY_BIRTHDATE = User.JSONRep.KEY_BIRTHDATE;
    public static String KEY_YEAR = User.JSONRep.KEY_YEAR;
    public static String KEY_MONTH = User.JSONRep.KEY_MONTH;
    public static String KEY_DAY = User.JSONRep.KEY_DAY;

    public static String KEY_CITY = User.JSONRep.KEY_CITY;

    public static String KEY_GENDER = User.JSONRep.KEY_GENDER;
    public static String VALUE_MALE = User.JSONRep.VALUE_MALE;
    public static String VALUE_FEMALE = User.JSONRep.VALUE_FEMALE;

    private Bundle mTalkData;

    public Talk(Bundle talkData) {
        mTalkData = talkData;
    }


    public User getSender(){
        User sender = new User();

        try {
            sender.setUserId(mTalkData.getString(KEY_USERID));
            Log.d(LOG_TAG, "mTalkData userid: " + mTalkData.getString(KEY_USERID));
            sender.setCity(mTalkData.getString(KEY_CITY));
            Log.d(LOG_TAG, "mTalkData city: " + mTalkData.getString(KEY_CITY));
            sender.setUsername(mTalkData.getString(KEY_USERNAME));
            Log.d(LOG_TAG, "mTalkData username: " + mTalkData.getString(KEY_USERNAME));
            sender.setBirthdate(FormatHelper.getDateFromJSON(new JSONObject((String) mTalkData.get(KEY_BIRTHDATE))));
            Log.d(LOG_TAG, "mTalkData birthdate: " + FormatHelper.getDateFromJSON(new JSONObject((String)mTalkData.get(KEY_BIRTHDATE))).toString());
            sender.setGender(mTalkData.getString(KEY_GENDER).equals(VALUE_MALE));
            Log.d(LOG_TAG, "mTalkData gender: " + mTalkData.getString(KEY_GENDER).equals(VALUE_MALE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sender;
    }

    public String getText() {
        return mTalkData.getString(KEY_TEXT);
    }
    
}
