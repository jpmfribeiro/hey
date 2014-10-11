package com.zerolabs.hey;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zerolabs.hey.DiscoveryHelper.WLANP2PDiscovery;

import java.util.List;

/**
 * Created by march_000 on 11.10.2014.
 */
public class MainListFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wlanp2PDiscovery = new WLANP2PDiscovery(getActivity());
        wlanp2PDiscovery.initialize();
    }

    public MainListFragment() {
    }

    Button button;
    WLANP2PDiscovery wlanp2PDiscovery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        button = (Button)rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wlanp2PDiscovery.discoverMACAdresses(new WLANP2PDiscovery.MACListener() {
                    @Override
                    public void MACReturn(List<String> MACList) {

                    }
                });
            }
        });
        return rootView;
    }
}