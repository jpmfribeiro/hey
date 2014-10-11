package com.zerolabs.hey.comm;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.zerolabs.hey.model.User;

/**
 * Created by jpedro on 11.10.14.
 */
public class ServerComm {

// FACEBOOK REQUESTS

    // Make a Facebook API call to get user data
    public void getFacebookData(final Session session, final OnGetFacebookDataListener listener) {
        // Make a Facebook API call to get user data and define a
        // new callback to handle the response.
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
                                listener.onResponse(user);
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

    public void registerUser(User user, OnRegisterUserListener listener) {

    }



// INTERFACES

    public interface OnGetFacebookDataListener {
        public void onResponse(GraphUser user);
        public void onErrorResponse(FacebookRequestError error);
    }

    public interface OnRegisterUserListener {
        public void onResponse();
        public void onErrorResponse();
    }


}
