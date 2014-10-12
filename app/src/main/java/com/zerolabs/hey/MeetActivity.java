package com.zerolabs.hey;

import android.app.Activity;
import android.os.Bundle;
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


public class MeetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);

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

    ServerComm mServerComm;
    TextView mUserNameTextView;
    TextView mLocationTextView;
    TextView mGenderTextView;
    Session mFacebookSession;
    EditText mChatEditText;
    ViewGroup mChatHistoryContainer;
    private ProfilePictureView mProfilePictureView;


    public void addTextToChat(String text, boolean ownText){
        View newView;
        if(ownText)
            newView = LayoutInflater.from(this).inflate(R.layout.chat_list_item, mChatHistoryContainer);
        else
            newView = LayoutInflater.from(this).inflate(R.layout.chat_list_item2, mChatHistoryContainer);

        TextView textView = (TextView)newView.findViewById(R.id.text);
        textView.setText(text);
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
}
