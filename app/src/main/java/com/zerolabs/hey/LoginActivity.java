package com.zerolabs.hey;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.FacebookRequestError;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.zerolabs.hey.comm.RequestManager;
import com.zerolabs.hey.comm.ServerComm;
import com.zerolabs.hey.model.User;

import java.util.Arrays;


public class LoginActivity extends FragmentActivity {

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String LOG_TAG = LoginActivity.class.getSimpleName();

    String SENDER_ID = "Your-Sender-ID";

    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestManager.init(this);  // TODO: IS THIS THE RIGHT PLACE TO INITIALIZE THE REQUESTMANAGER ?

        // Check device for Play Services APK.
        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.


            setContentView(R.layout.activity_login);
            if (savedInstanceState == null) {
                mLoginFragment = new LoginFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.login_container, mLoginFragment)
                        .commit();
            } else {
                mLoginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.login_container);
            }
        } else {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(LOG_TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * LoginFragment handles the facebook login process.
     */
    public static class LoginFragment extends Fragment {

        private static final String LOG_TAG = LoginFragment.class.getSimpleName();

        private UiLifecycleHelper mUiHelper;
        private ServerComm mServerComm;

        public LoginFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);

            LoginButton authButton = (LoginButton) rootView.findViewById(R.id.authButton);
            authButton.setFragment(this);
            authButton.setReadPermissions(Arrays.asList("public_profile", "user_birthday", "user_location"));

            return rootView;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mUiHelper = new UiLifecycleHelper(getActivity(), mCallback);
            mUiHelper.onCreate(savedInstanceState);
            mServerComm = new ServerComm(getActivity());
        }

        @Override
        public void onResume() {
            super.onResume();

            // For scenarios where the main activity is launched and user
            // session is not null, the session state change notification
            // may not be triggered. Trigger it if it's open/closed.
            Session session = Session.getActiveSession();
            if (session != null &&
                    (session.isOpened() || session.isClosed()) ) {
                onSessionStateChange(session, session.getState(), null);
            }

            mUiHelper.onResume();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            mUiHelper.onActivityResult(requestCode, resultCode, data);
        }

        @Override
        public void onPause() {
            super.onPause();
            mUiHelper.onPause();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mUiHelper.onDestroy();
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            mUiHelper.onSaveInstanceState(outState);
        }

        private void onSessionStateChange(Session session, SessionState state, Exception exception) {
            if (state.isOpened()) {
                Log.i(LOG_TAG, "Logged in...");

                final WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                mServerComm.getFacebookData(session, new ServerComm.OnGetFacebookDataListener() {
                    @Override
                    public void onResponse(User user) {
                        user.setMacAddress(wifiManager.getConnectionInfo().getMacAddress());
                        mServerComm.registerUser(user, new ServerComm.OnRegisterUserListener() {
                            @Override
                            public void onResponse(boolean successful) {
                                if(successful){

                                }

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                Toast.makeText(getActivity(), "login successful", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onErrorResponse(FacebookRequestError error) {
                        Log.d(getClass().toString(), error.toString());
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            } else if (state.isClosed()) {
                Log.i(LOG_TAG, "Logged out...");
            }
        }

        private Session.StatusCallback mCallback = new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                onSessionStateChange(session, state, exception);
            }
        };
    }
}
