package com.zerolabs.hey.model;

import com.facebook.model.GraphUser;
import com.zerolabs.hey.helpers.FormatHelper;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jpedro on 11.10.14.
 */
public class User {

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

    public JSONObject toJson() {
        return null;
    }

    public static User fromGraphUser(GraphUser graphUser) {
        User resultUser = new User();

        resultUser.setUserId(graphUser.getId());
        resultUser.setBirthdate(FormatHelper.getDateFromString(graphUser.getBirthday()));
        resultUser.setUsername(graphUser.getName());
        resultUser.setCity(graphUser.getLocation().getName());

        return resultUser;
    }

}
