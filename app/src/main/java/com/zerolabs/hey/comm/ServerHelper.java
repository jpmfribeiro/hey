package com.zerolabs.hey.comm;

/**
 * Created by jpedro on 11.10.14.
 */
public class ServerHelper {

  // BASE URLs

    // Base URL for all communication with server
    public static String BASE_URL = "";
    // Base URL for User operations
    public static String USER_BASE_URL = BASE_URL + "/user";
    // Base URL for Discover operations
    public static String DISCOVER_BASE_URL = BASE_URL + "/discover";

  // USER URLs

    // URL for registering/updating an user
    public static String REGISTER_URL = USER_BASE_URL + "/register";

  // DISCOVER URLs

    // URL for discovering which MAC-Addresses correspond to real users
    public static String USERS_FROM_MAC_URL = DISCOVER_BASE_URL + "/usersfrommac";

}
