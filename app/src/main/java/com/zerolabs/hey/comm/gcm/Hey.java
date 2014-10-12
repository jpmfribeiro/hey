package com.zerolabs.hey.comm.gcm;

import android.os.Bundle;
import android.util.Log;

import com.zerolabs.hey.helpers.FormatHelper;
import com.zerolabs.hey.model.User;

import org.json.JSONObject;

/**
 * Created by jpedro on 12.10.14.
 */
public class Hey {

    public static String LOG_TAG = Hey.class.getSimpleName();

    // CONSTANTS

    public static String KEY_IS_HEY = "ishey";  // will be 1 (true) if it is a hey!
    public static String KEY_SENDER = "sender";

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


    // DATA

    private Bundle mHeyData;

    // CONSTRUCTOR

    public Hey(Bundle heyData) {
        mHeyData = heyData;
    }

    // GETTERS

    public User getSender(){
        User sender = new User();

        try {
            sender.setUserId(mHeyData.getString(KEY_USERID));
            Log.d(LOG_TAG, "mHeyData userid: " + mHeyData.getString(KEY_USERID));
            sender.setCity(mHeyData.getString(KEY_CITY));
            Log.d(LOG_TAG, "mHeyData city: " + mHeyData.getString(KEY_CITY));
            sender.setUsername(mHeyData.getString(KEY_USERNAME));
            Log.d(LOG_TAG, "mHeyData username: " + mHeyData.getString(KEY_USERNAME));
            sender.setBirthdate(FormatHelper.getDateFromJSON(new JSONObject((String)mHeyData.get(KEY_BIRTHDATE))));
            Log.d(LOG_TAG, "mHeyData birthdate: " + FormatHelper.getDateFromJSON(new JSONObject((String)mHeyData.get(KEY_BIRTHDATE))).toString());
            sender.setGender(mHeyData.getString(KEY_GENDER).equals(VALUE_MALE));
            Log.d(LOG_TAG, "mHeyData gender: " + mHeyData.getString(KEY_GENDER).equals(VALUE_MALE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sender;
    }



}
