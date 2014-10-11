package com.zerolabs.hey.comm;

/**
 * Created by jpedro on 11.10.14.
 */
public class ServerHelper {

  // BASE URLs

    // Base URL for all communication with server
    public static String BASE_URL = "http://heyserver-env-dfdpk3pybp.elasticbeanstalk.com";
    // Base URL for User operations
    public static String USER_BASE_URL = BASE_URL + "/user";
    // Base URL for Discover operations
    public static String DISCOVER_BASE_URL = BASE_URL + "/discover";

  // USER URLs

    // URL for registering/updating an user
    public static String REGISTER_URL = USER_BASE_URL + "/register";
    // URL for sending a hey!
    public static String HEY_URL = USER_BASE_URL + "/hey";
    // URL for sending a message to another user per chat
    public static String TALK_URL = USER_BASE_URL + "/talk";

  // DISCOVER URLs

    // URL for discovering which MAC-Addresses correspond to real users
    public static String USERS_FROM_MAC_URL = DISCOVER_BASE_URL + "/getusersfrommac";

}
