package com.zerolabs.hey.model;

/**
 * Created by jpedro on 11.10.14.
 */
public class User {

    // ATTRIBUTES

    private String mUsername;
    private String mAccessToken;
    private String mMacAddress;

    private String mCity;

    private int mAge;
    private int mMinAge;
    private int mMaxAge;

    private boolean mIsMale;
    private boolean mLikesMale;
    private boolean mLikesFemale;

    public User() { }

    // SETTERS

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public void setMacAddress(String macAddress) {
        mMacAddress = macAddress;
    }

    public void setAge(int age) {
        mAge = age;
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

    public int getAge() {
        return mAge;
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
}
