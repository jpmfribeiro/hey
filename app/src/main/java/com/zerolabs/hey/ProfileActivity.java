package com.zerolabs.hey;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.Session;
import com.zerolabs.hey.comm.ServerComm;
import com.zerolabs.hey.model.User;


public class ProfileActivity extends Activity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_profile);
        mUserNameTextView = (TextView)findViewById(R.id.profile_username_textview);
        mServerComm = new ServerComm();
        mFacebookSession = Session.getActiveSession();
        if(mFacebookSession!=null) {
            mServerComm.getFacebookData(null, new ServerComm.OnGetFacebookDataListener() {
                @Override
                public void onResponse(User user) {
                    mUserNameTextView.setText(user.getUsername());
                }

                @Override
                public void onErrorResponse(FacebookRequestError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    ServerComm mServerComm;
    TextView mUserNameTextView;
    Session mFacebookSession;

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
