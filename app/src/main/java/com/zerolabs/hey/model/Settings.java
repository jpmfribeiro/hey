package com.zerolabs.hey.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jpedro on 11.10.14.
 */
public class Settings {



    public static void storeUserData(Context context, User user) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        


    }

    public static User getUser() {
        return new User();
    }

}
