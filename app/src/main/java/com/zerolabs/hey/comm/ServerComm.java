package com.zerolabs.hey.comm;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookRequestError;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.zerolabs.hey.comm.messages.UsersFromMacRequestMessage;
import com.zerolabs.hey.comm.messages.RegisterRequestMessage;
import com.zerolabs.hey.comm.messages.RegisterResponseMessage;
import com.zerolabs.hey.comm.messages.UsersFromMacResponseMessage;
import com.zerolabs.hey.model.Settings;
import com.zerolabs.hey.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by jpedro on 11.10.14.
 */
public class ServerComm {

// ATTRIBUTES

    private Context mContext;
    private RequestQueue mQueue;


// CONSTRUCTOR

    public ServerComm(Context context) {
        mContext = context;
        mQueue = RequestManager.getRequestQueue();
        if (mQueue == null) mQueue = Volley.newRequestQueue(mContext);  // TODO IS THIS NECESSARY?
    }


// FACEBOOK REQUESTS

    // Make a Facebook API call to get user data
    public void getFacebookData(final Session session, final OnGetFacebookDataListener listener) {
        // Make a Facebook API call to get user data and define a
        // new callback to handle the response.
        com.facebook.Request request = com.facebook.Request.newMeRequest(session,
                new com.facebook.Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, com.facebook.Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (user != null) {

                                User heyUser = User.fromGraphUser(user);
                                heyUser.setAccessToken(session.getAccessToken());

                                listener.onResponse(heyUser);
                            }
                        }
                        if (response.getError() != null) {
                            listener.onErrorResponse(response.getError());
                        }
                    }
                });
        request.executeAsync();
    }


// SERVER REQUESTS

    public void registerUser(User user, final OnRegisterUserListener listener) {
        // Create JSON from user
        RegisterRequestMessage message = new RegisterRequestMessage(user);
        JSONObject registerJson;

        try {
            registerJson = message.toJson();
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        // Store user data locally
        Settings.storeUserData(mContext, user);

        // Make Volley JSONObjectRequest
        JsonObjectRequest registerRequest = new JsonObjectRequest
                (Request.Method.POST, ServerHelper.REGISTER_URL, registerJson, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonResponse) {
                        boolean successful;
                        try {
                            RegisterResponseMessage response = new RegisterResponseMessage(jsonResponse);
                            successful = response.wasSuccessful();
                        } catch(JSONException e) {
                            e.printStackTrace();
                            successful = false;
                        }

                        listener.onResponse(successful);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        listener.onErrorResponse(volleyError);
                    }
                });

        // add to queue
        mQueue.add(registerRequest);
    }

    public void getUsersFromMacAddresses(List<String> macAddresses, final OnGetUsersListener listener) {

        UsersFromMacRequestMessage message = new UsersFromMacRequestMessage(macAddresses);
        JSONObject usersFromMacJson;
        try {
            usersFromMacJson = message.toJson();
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        // Make Volley JSONObjectRequest
        JsonObjectRequest usersFromMacRequest = new JsonObjectRequest
                (Request.Method.POST, ServerHelper.USERS_FROM_MAC_URL, usersFromMacJson, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonResponse) {
                        boolean successful;
                        UsersFromMacResponseMessage response = new UsersFromMacResponseMessage(jsonResponse);
                        List<User> users = null;

                        try {
                            successful = response.wasSuccessful();
                        } catch(JSONException e) {
                            e.printStackTrace();
                            successful = false;
                        }

                        if (successful) {
                            try {
                                users = response.getUsers();
                            } catch(JSONException e) {
                                e.printStackTrace();
                                users = null;
                            }
                        }

                        listener.onResponse(successful, users);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        listener.onErrorResponse(volleyError);
                    }
                });

        // add to queue
        mQueue.add(usersFromMacRequest);
    }

    public void sendHey(User userToHey, final OnHeyListener listener) {
        // TODO
    }

// INTERFACES

    public interface OnGetFacebookDataListener {
        public void onResponse(User user);
        public void onErrorResponse(FacebookRequestError error);
    }

    public interface OnRegisterUserListener {
        public void onResponse(boolean successful);
        public void onErrorResponse(VolleyError error);
    }

    public interface OnGetUsersListener {
        public void onResponse(boolean successful, List<User> retrievedUsers);
        public void onErrorResponse(VolleyError error);
    }

    public interface OnHeyListener {
        public void onResponse(boolean successful);
        public void onErrorResponse(VolleyError error);
    }

}
