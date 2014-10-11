package com.zerolabs.hey.DiscoveryHelper;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by march_000 on 11.10.2014.
 */
public class WLANP2PDiscovery {
    WifiP2pManager mWifiP2pManager;
    WifiP2pManager.Channel mChannel;
    MACListener cMacListener;
    Context mContext;

    public WLANP2PDiscovery(Context context){
        mWifiP2pManager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        mContext = context;
    }


    public void initialize(){
        mChannel = mWifiP2pManager.initialize(mContext, mContext.getMainLooper(), null);
    }

    public void terminate(){
        mWifiP2pManager.cancelConnect(mChannel, null);
    }


    public void discoverMACAdresses(MACListener macListener){
        cMacListener = macListener;
        mWifiP2pManager.discoverPeers(mChannel, mPeerToMACListener);
    }

    PeerToMACListener mPeerToMACListener = new PeerToMACListener();
    class PeerToMACListener implements WifiP2pManager.ActionListener {

        @Override
        public void onSuccess() {
            mWifiP2pManager.requestPeers(mChannel, mPeerListToMACListener);
        }

        @Override
        public void onFailure(int i) {
            Log.v("MAC Address: ", "failure");
        }
    }

    PeerListToMACListener mPeerListToMACListener = new PeerListToMACListener();
    class PeerListToMACListener implements WifiP2pManager.PeerListListener{
        @Override
        public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
            Collection<WifiP2pDevice> wifiP2pDeviceCollection = wifiP2pDeviceList.getDeviceList();

            List<String> returnMACList = new LinkedList<String>();
            Log.v("MAC Address: ", "got Response");
            for(WifiP2pDevice wifiP2pDevice: wifiP2pDeviceCollection){
                returnMACList.add(wifiP2pDevice.deviceAddress.substring(2));
                Log.v("MAC Address: ", wifiP2pDevice.deviceAddress);
            }
            cMacListener.MACReturn(returnMACList);
        }
    }


    public interface MACListener{
        void MACReturn(List<String> MACList);
    }
}
