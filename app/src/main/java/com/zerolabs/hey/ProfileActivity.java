package com.zerolabs.hey;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.FacebookRequestError;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.ProfilePictureView;
import com.zerolabs.hey.UIHelper.RangeSeekBar;
import com.zerolabs.hey.comm.ServerComm;
import com.zerolabs.hey.model.Settings;
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
        mMenCheckBox = (CheckBox)findViewById(R.id.men_checkbox);
        mWomenCheckBox = (CheckBox)findViewById(R.id.women_checkbox);
        mAgeRangeTextView = (TextView)findViewById(R.id.age_range_number_textview);
        mAgeRangeSeekBar = new RangeSeekBar<Integer>(16, 55, context);
        ((ViewGroup)findViewById(R.id.rangeSeekBarFrame)).addView(mAgeRangeSeekBar);
        mAgeRangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                if(maxValue!=55) {
                    mAgeRangeTextView.setText(" " + minValue + "-" + maxValue + " ");
                } else {
                    mAgeRangeTextView.setText(" " + minValue + "-" + maxValue + "+");
                }
            }
        });

        mUser = Settings.getUser(context);
        mAgeRangeSeekBar.setSelectedMinValue(mUser.getMinAge());
        mAgeRangeSeekBar.setSelectedMaxValue(mUser.getMaxAge());
        mWomenCheckBox.setChecked(mUser.likesFemale());
        mMenCheckBox.setChecked(mUser.likesMale());


        mAgeRangeSeekBar.setNotifyWhileDragging(true);

        mProfilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
        mProfilePictureView.setCropped(true);
        mServerComm = new ServerComm(this);
        mFacebookSession = Session.getActiveSession();

        if(mFacebookSession == null) mFacebookSession = Session.openActiveSession(this, true, mCallback);

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

    public void submit(View view){
        mUser.setLikesFemale(mWomenCheckBox.isChecked());
        mUser.setLikesMale(mMenCheckBox.isChecked());
        mUser.setMinAge(mAgeRangeSeekBar.getSelectedMinValue());
        mUser.setMaxAge(mAgeRangeSeekBar.getSelectedMaxValue());
        mServerComm.registerUser(mUser, new ServerComm.OnRegisterUserListener() {
            @Override
            public void onResponse(boolean successful) {
                if(successful) {
                    // Intent intent = new Intent(context, MainActivity.class);
                    // startActivity(intent);
                    Toast.makeText(context, "saved settings", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(context, "error while saving settings", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error while saving settings: "+error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mUiHelper.onSaveInstanceState(outState);
    }

    User mUser;

    ServerComm mServerComm;
    TextView mUserNameTextView;
    TextView mLocationTextView;
    TextView mGenderTextView;
    Session mFacebookSession;
    CheckBox mWomenCheckBox;
    CheckBox mMenCheckBox;
    TextView mAgeRangeTextView;
    RangeSeekBar<Integer> mAgeRangeSeekBar;
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
