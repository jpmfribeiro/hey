package com.zerolabs.hey;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.widget.ProfilePictureView;
import com.zerolabs.hey.comm.ServerComm;
import com.zerolabs.hey.comm.gcm.GCMIntentService;


public class MeetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra(GCMIntentService.TALK_MESSAGE);
                addTextToChat(msg, false);
            }
        };

        mUserNameTextView = (TextView)findViewById(R.id.profile_username_textview);
        mLocationTextView = (TextView)findViewById(R.id.profile_location_textview);
        mGenderTextView = (TextView)findViewById(R.id.profile_gender_textview);
        mProfilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
        mProfilePictureView.setCropped(true);
        mServerComm = new ServerComm(this);
        mChatEditText = (EditText)findViewById(R.id.chat_edittext);
        mFacebookSession = Session.getActiveSession();
        mChatHistoryContainer = (ViewGroup)findViewById(R.id.chat_history);
    }


    BroadcastReceiver receiver;
    ServerComm mServerComm;
    TextView mUserNameTextView;
    TextView mLocationTextView;
    TextView mGenderTextView;
    Session mFacebookSession;
    EditText mChatEditText;
    ViewGroup mChatHistoryContainer;
    private ProfilePictureView mProfilePictureView;

    public void send(View view){
        addTextToChat(mChatEditText.getText().toString(), true);
    }

    public void addTextToChat(String text, boolean ownText){
        View newView;
        if(ownText)
            newView = LayoutInflater.from(this).inflate(R.layout.chat_list_item, mChatHistoryContainer, false);
        else
            newView = LayoutInflater.from(this).inflate(R.layout.chat_list_item2, mChatHistoryContainer, false);

        TextView textView = (TextView)newView.findViewById(R.id.text);
        textView.setText(text);
        mChatHistoryContainer.addView(newView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meet, menu);
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

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver), new IntentFilter(GCMIntentService.TALK_RESULT));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }
}
