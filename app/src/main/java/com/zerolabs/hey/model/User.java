package com.zerolabs.hey.model;

import android.util.Log;

import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.zerolabs.hey.helpers.FormatHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jpedro on 11.10.14.
 *
 * Representation of an User.
 *
 * ATTENTION! Getters may return NULL or EMPTY Strings, it is the client's responsibility to check.
 *
 */
public class User {

    public static String LOG_TAG = User.class.getSimpleName();

// ATTRIBUTES

    private String mUserId; // FacebookID
    private String mGCMId;  // TODO: THE GCM_ID IS NOT SAVED AS A PREFERENCE FOR NOW. SHOULD IT BE?

    private String mUsername;
    private String mAccessToken;
    private String mMacAddress;

    private String mCity;

    private Date mBirthdate;
    private int mMinAge;
    private int mMaxAge;

    private boolean mIsMale;
    private boolean mLikesMale;
    private boolean mLikesFemale;

    public User() { }

// SETTERS

    public void setUserId(String userid) {
        mUserId = userid;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public void setMacAddress(String macAddress) {
        mMacAddress = macAddress;
    }

    public void setBirthdate(Date birthdate) {
        mBirthdate = birthdate;
    }

    public void setMinAge(int minAge) {
        mMinAge = minAge;
    }

    public void setMaxAge(int maxAge) {
        mMaxAge = maxAge;
    }

    public void setGender(boolean isMale) {
        mIsMale = isMale;
    }

    public void setLikesMale(boolean likesMale) {
        mLikesMale = likesMale;
    }

    public void setLikesFemale(boolean likesFemale) {
        mLikesFemale = likesFemale;
    }

    public void setCity(String city) { mCity = city; }

    public void setGCMId(String gcmId) { mGCMId = gcmId; }


// GETTERS


    public String getUsername() {
        return mUsername;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    public Date getBirthdate() {
        return mBirthdate;
    }

    public int getMinAge() {
        return mMinAge;
    }

    public int getMaxAge() {
        return mMaxAge;
    }

    public boolean isMale() {
        return mIsMale;
    }

    public boolean likesMale() {
        return mLikesMale;
    }

    public boolean likesFemale() {
        return mLikesFemale;
    }

    public String getCity() { return mCity; }

    public String getUserId() { return mUserId; }

    public String getGCMId() { return mGCMId; }

    public int getAge() {
        GregorianCalendar birthCal = new GregorianCalendar();
        birthCal.setTime(mBirthdate);

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);

        a = y - birthCal.get(Calendar.YEAR);
        if ((m < birthCal.get(Calendar.MONTH))
                || ((m == birthCal.get(Calendar.MONTH)) && (d < birthCal.get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if(a < 0)
            throw new IllegalArgumentException("Age < 0");

        return a;
    }


// REPRESENTATION HELPER METHODS

    public static User fromGraphUser(GraphUser graphUser) {
        User resultUser = new User();

        resultUser.setUserId(graphUser.getId());
        Date birthdate = FormatHelper.getDateFromString(graphUser.getBirthday());

        if (birthdate == null) Log.e(LOG_TAG, "Facebook didn't send any birthday!");
        else resultUser.setBirthdate(birthdate);
        
        resultUser.setUsername(graphUser.getName());

        GraphPlace location = graphUser.getLocation();
        if (location != null) resultUser.setCity(location.getName());
        else Log.e(LOG_TAG, "Facebook didn't send any location!");

        String gender = (String)graphUser.getProperty("gender");
        if (gender != null) resultUser.setGender(gender.equals("male"));
        else Log.e(LOG_TAG, "Facebook didn't send any gender information");

        return resultUser;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject userJson = new JSONObject();

        userJson.put(JSONRep.KEY_ACCESS_TOKEN, getAccessToken());

        JSONObject birthdateJson = new JSONObject();
        GregorianCalendar birthdateCal = (GregorianCalendar)Calendar.getInstance();
        birthdateCal.setTime(getBirthdate());
        birthdateJson.put(JSONRep.KEY_YEAR, birthdateCal.get(Calendar.YEAR));
        birthdateJson.put(JSONRep.KEY_MONTH, birthdateCal.get(Calendar.MONTH) + 1);
        birthdateJson.put(JSONRep.KEY_DAY, birthdateCal.get(Calendar.DAY_OF_MONTH));

        userJson.put(JSONRep.KEY_BIRTHDATE, birthdateJson);
        userJson.put(JSONRep.KEY_USERID, getUserId());
        userJson.put(JSONRep.KEY_CITY, getCity());
        userJson.put(JSONRep.KEY_GCMID, getGCMId());
        userJson.put(JSONRep.KEY_GENDER, isMale() ? JSONRep.VALUE_MALE : JSONRep.VALUE_MALE);
        userJson.put(JSONRep.KEY_LIKES_FEMALE, likesFemale());
        userJson.put(JSONRep.KEY_LIKES_MALE, likesMale());
        userJson.put(JSONRep.KEY_MAC_ADDRESS, getMacAddress());
        userJson.put(JSONRep.KEY_MAX_AGE, getMaxAge());
        userJson.put(JSONRep.KEY_MIN_AGE, getMinAge());
        userJson.put(JSONRep.KEY_USERNAME, getUsername());

        return userJson;
    }

    public static User fromJson(JSONObject userJson) {
        User resultUser = new User();

        resultUser.setCity(userJson.optString(JSONRep.KEY_CITY));
        resultUser.setAccessToken(userJson.optString(JSONRep.KEY_ACCESS_TOKEN));
        resultUser.setBirthdate(FormatHelper.getDateFromString(userJson.optString(JSONRep.KEY_BIRTHDATE)));
        resultUser.setGender(userJson.optBoolean(JSONRep.KEY_GENDER, true));
        resultUser.setLikesFemale(userJson.optBoolean(JSONRep.KEY_LIKES_FEMALE, true));
        resultUser.setLikesMale(userJson.optBoolean(JSONRep.KEY_LIKES_MALE, true));
        resultUser.setMacAddress(userJson.optString(JSONRep.KEY_MAC_ADDRESS));
        resultUser.setMaxAge(userJson.optInt(JSONRep.KEY_MAX_AGE, JSONRep.DEFAULT_MAX_AGE));
        resultUser.setMinAge(userJson.optInt(JSONRep.KEY_MIN_AGE, JSONRep.DEFAULT_MIN_AGE));
        resultUser.setUsername(userJson.optString(JSONRep.KEY_USERNAME));
        resultUser.setUserId(userJson.optString(JSONRep.KEY_USERID));
        resultUser.setGCMId(userJson.optString(JSONRep.KEY_GCMID));

        return resultUser;
    }

// REPRESENTATION HELPER CLASSES

    public static class JSONRep {
        public static String KEY_USERID         = "facebookid";
        public static String KEY_GCMID          = "gcmregistrationid";
        public static String KEY_USERNAME       = "name";
        public static String KEY_ACCESS_TOKEN   = "accesstoken";
        public static String KEY_MAC_ADDRESS    = "macaddress";
        public static String KEY_CITY           = "city";

        public static String KEY_BIRTHDATE      = "birthday";
        public static String KEY_YEAR           = "year";
        public static String KEY_MONTH          = "month";
        public static String KEY_DAY            = "day";

        public static String KEY_MIN_AGE        = "talkstominage";
        public static String KEY_MAX_AGE        = "talkstomaxage";
        public static int DEFAULT_MAX_AGE       = 120;
        public static int DEFAULT_MIN_AGE       = 0;

        public static String KEY_GENDER         = "gender";
        public static String VALUE_MALE         = "M";
        public static String VALUE_FEMALE       = "W";

        public static String KEY_LIKES_MALE     = "talkstomale";
        public static String KEY_LIKES_FEMALE   = "talkstofemale";
    }

}
