package com.zerolabs.hey;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.widget.ProfilePictureView;
import com.zerolabs.hey.DiscoveryHelper.WLANP2PDiscovery;
import com.zerolabs.hey.comm.ServerComm;
import com.zerolabs.hey.model.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by march_000 on 11.10.2014.
 */
public class MainListFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wlanp2PDiscovery = new WLANP2PDiscovery(getActivity());
        mServerComm = new ServerComm(getActivity());
        wlanp2PDiscovery.initialize();

        wlanp2PDiscovery.automateDiscovery(new WLANP2PDiscovery.MACListener() {
            @Override
            public void MACReturn(List<String> MACList) {
                mServerComm.getUsersFromMacAddresses(MACList, new ServerComm.OnGetUsersListener() {
                    @Override
                    public void onResponse(boolean successful, List<User> retrievedUsers) {
                        if(successful) {
                            Log.v(getClass().toString(), retrievedUsers.toString());
                            setNearUsers(retrievedUsers);
                        }
                        else
                        {
                            Log.v(getClass().toString(), "unsuccessful");
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(getClass().toString(), error.toString());
                    }
                });
            }
        }, 4000);

    }

    public MainListFragment() {
    }

    ViewGroup listRootView;
    Button button;
    Button profilButton;
    WLANP2PDiscovery wlanp2PDiscovery;
    ServerComm mServerComm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listRootView = (ViewGroup)rootView.findViewById(R.id.listContainer);




        profilButton = (Button)rootView.findViewById(R.id.button2);
        profilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }



    HashMap <String, View> viewHashMap = new HashMap<String, View>();
    HashMap <View, User> viewUserHashMap = new HashMap<View, User>();
    HashMap <View, Boolean> viewIsActivatedHashMap = new HashMap<View, Boolean>();
    List<User> currentUsers = new LinkedList<User>();

    void setNearUsers(List<User> users){
        List<User> oldUsers = currentUsers;
        List<User> newUsers = users;

        Log.v("newUsers length: ", newUsers.size()+"");
        //TODO: n*log(n)
        //add new Users to view
        for(User newUser: newUsers){
            boolean foundOld = false;
            for(User oldUser: oldUsers){
                if(oldUser.getMacAddress().equals(newUser.getMacAddress())){
                    foundOld = true;
                    break;
                }
            }
            if(!foundOld){
                View newView = LayoutInflater.from(getActivity()).inflate(R.layout.main_list_item, listRootView, false);

                newView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Boolean isActivated = viewIsActivatedHashMap.get(view);
                        if(!isActivated){
                            view.setBackgroundColor(getResources().getColor(R.color.selectedGreen));
                            viewIsActivatedHashMap.put(view, true);
                            mServerComm.sendHey(viewUserHashMap.get(view), new ServerComm.OnHeyListener() {
                                @Override
                                public void onResponse(boolean successful) {
                                    if(successful)
                                        Log.v(getClass().toString(), "Hey successful");
                                    else
                                        Log.v(getClass().toString(), "Hey unsuccessful");
                                }

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.v(getClass().toString(), "Hey unsuccessful: "+error.toString());
                                }
                            });
                        }
                        else{
                            //view.setBackgroundColor(0x00000000);
                            //viewIsActivatedHashMap.put(view, false);
                            //TODO user doesn't want to hey the other user
                        }

                    }
                });


                ((TextView)newView.findViewById(R.id.profile_username_textview)).setText(newUser.getUsername());
                ProfilePictureView profilePictureView = ((ProfilePictureView)newView.findViewById(R.id.selection_profile_pic));
                profilePictureView.setCropped(true);
                profilePictureView.setProfileId(newUser.getUserId());

                ((TextView)newView.findViewById(R.id.profile_location_textview)).setText(newUser.getCity());
                ((TextView)newView.findViewById(R.id.profile_gender_textview)).setText(newUser.isMale() ? "man" : "woman");

                listRootView.addView(newView);
                viewHashMap.put(newUser.getMacAddress(), newView);
                viewUserHashMap.put(newView, newUser);
                viewIsActivatedHashMap.put(newView, false);
            }
        }
        //remove old users that are no longer on the list
        for(User oldUser: oldUsers){
            boolean foundNew = false;
            for(User newUser: newUsers){
                if(oldUser.getMacAddress().equals(newUser.getMacAddress())){
                    foundNew = true;
                    break;
                }
            }
            if(!foundNew){
                View currentView = viewHashMap.get(oldUser.getMacAddress());
                listRootView.removeView(currentView);
                viewHashMap.remove(oldUser.getMacAddress());
                viewUserHashMap.remove(currentView);
                viewIsActivatedHashMap.remove(currentView);
            }
        }
        currentUsers = users;
    }



}