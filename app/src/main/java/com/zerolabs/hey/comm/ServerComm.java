package com.zerolabs.hey.comm;

import com.android.volley.VolleyError;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.zerolabs.hey.model.User;

import java.util.List;

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
        // TODO
        // Create JSON from user
        // user.toJson()

        // Store user data locally
        // Settings.storeUser(context);

        // Make Volley JSONObjectRequest
            // Call listener

        // add to queue
    }

    public void getUsersFromMac(List<String> macaddresses, final OnGetUsersListener listener) {
        // TODO
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
