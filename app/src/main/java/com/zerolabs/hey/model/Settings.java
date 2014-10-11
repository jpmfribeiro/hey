package com.zerolabs.hey.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zerolabs.hey.R;

/**
 * Created by jpedro on 11.10.14.
 */
public class Settings {

    public static void storeUserData(Context context, User user) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(context.getString(R.string.pref_username), user.getUsername());
        editor.putString(context.getString(R.string.pref_accesstoken), user.getAccessToken());
        editor.putString(context.getString(R.string.pref_macaddress), user.getMacAddress());
        editor.putInt(context.getString(R.string.pref_age), user.getAge());
        editor.putInt(context.getString(R.string.pref_minage), user.getMinAge());
        editor.putInt(context.getString(R.string.pref_maxage), user.getMaxAge());
        editor.putString(context.getString(R.string.pref_city), user.getCity());
        editor.putBoolean(context.getString(R.string.pref_ismale), user.isMale());
        editor.putBoolean(context.getString(R.string.pref_likesfemale), user.likesFemale());
        editor.putBoolean(context.getString(R.string.pref_likesmale), user.likesMale());

        editor.commit();


    }

    public static User getUser(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        User user = new User();

        user.setAccessToken(settings.getString(context.getString(R.string.pref_accesstoken), ""));
        user.setAge(settings.getInt(context.getString(R.string.pref_age), -1));
        user.setGender(settings.getBoolean(context.getString(R.string.pref_ismale), true));
        user.setLikesFemale(settings.getBoolean(context.getString(R.string.pref_likesfemale), true));
        user.setLikesMale(settings.getBoolean(context.getString(R.string.pref_likesmale), true));
        user.setMacAddress(settings.getString(context.getString(R.string.pref_macaddress), ""));
        user.setMaxAge(settings.getInt(context.getString(R.string.pref_maxage), 120));
        user.setCity(settings.getString(context.getString(R.string.pref_city), ""));
        user.setMinAge(settings.getInt(context.getString(R.string.pref_minage), -1));
        user.setUsername(settings.getString(context.getString(R.string.pref_username), ""));

        return user;

    }

}
