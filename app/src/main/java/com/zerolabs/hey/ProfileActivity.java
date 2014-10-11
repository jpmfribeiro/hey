package com.zerolabs.hey;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.ProfilePictureView;
import com.zerolabs.hey.comm.ServerComm;
import com.zerolabs.hey.model.User;


public class ProfileActivity extends Activity {

    public static String LOG_TAG = ProfileActivity.class.getSimpleName();

    Context context;

    private UiLifecycleHelper mUiHelper;
    private Session.StatusCallback mCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            // onSessionStateChange(session, state, exception);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUiHelper = new UiLifecycleHelper(ProfileActivity.this, mCallback);
        mUiHelper.onCreate(savedInstanceState);

        context = this;
        setContentView(R.layout.activity_profile);
        mUserNameTextView = (TextView)findViewById(R.id.profile_username_textview);
        mLocationTextView = (TextView)findViewById(R.id.profile_location_textview);
        mGenderTextView = (TextView)findViewById(R.id.profile_gender_textview);
        mProfilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
        mProfilePictureView.setCropped(true);
        mServerComm = new ServerComm(this);
        mFacebookSession = Session.getActiveSession();

        if(mFacebookSession != null) {
            Log.d(LOG_TAG, "Session is: " + mFacebookSession.toString() + " with access token " + mFacebookSession.getAccessToken());
            mServerComm.getFacebookData(mFacebookSession, new ServerComm.OnGetFacebookDataListener() {
                @Override
                public void onResponse(User user) {
                    mUserNameTextView.setText(user.getUsername() + ", " + user.getAge());
                    mLocationTextView.setText(user.getCity());
                    mGenderTextView.setText(user.isMale() ? "man" : "woman");
                    mProfilePictureView.setProfileId(user.getUserId());
                }

                @Override
                public void onErrorResponse(FacebookRequestError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d(getClass().toString(), error.toString());
                }
            });
        } else {
            Log.d(LOG_TAG, "No session!!");
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
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

    ServerComm mServerComm;
    TextView mUserNameTextView;
    TextView mLocationTextView;
    TextView mGenderTextView;
    Session mFacebookSession;
    private ProfilePictureView mProfilePictureView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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

}
