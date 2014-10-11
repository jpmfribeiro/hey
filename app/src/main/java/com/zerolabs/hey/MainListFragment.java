package com.zerolabs.hey;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    }

    public MainListFragment() {
    }

    ViewGroup listRootView;
    Button button;
    WLANP2PDiscovery wlanp2PDiscovery;
    ServerComm mServerComm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listRootView = (ViewGroup)rootView.findViewById(R.id.listContainer);
        button = (Button)rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                wlanp2PDiscovery.discoverMACAdresses(new WLANP2PDiscovery.MACListener() {
                    @Override
                    public void MACReturn(List<String> MACList) {
                        mServerComm.getUsersFromMacAddresses(MACList, new ServerComm.OnGetUsersListener() {
                            @Override
                            public void onResponse(boolean successful, List<User> retrievedUsers) {
                                Log.v(getClass().toString(), retrievedUsers.toString());
                                setNearUsers(retrievedUsers);
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(getClass().toString(), error.toString());
                            }
                        });
                    }
                });
                */


                View newView = LayoutInflater.from(getActivity()).inflate(R.layout.main_list_item, listRootView, false);
                listRootView.addView(newView);

            }
        });
        return rootView;
    }

    HashMap <String, View> viewHashMap = new HashMap<String, View>();
    List<User> currentUsers = new LinkedList<User>();

    void setNearUsers(List<User> users){
        List<User> oldUsers = users;
        List<User> newUsers = users;
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
                listRootView.addView(newView);
                viewHashMap.put(newUser.getMacAddress(), newView);
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
                listRootView.removeView(viewHashMap.get(oldUser.getMacAddress()));
                viewHashMap.remove(oldUser.getMacAddress());
            }
        }
    }



}