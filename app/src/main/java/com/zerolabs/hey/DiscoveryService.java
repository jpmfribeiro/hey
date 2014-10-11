package com.zerolabs.hey;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DiscoveryService extends Service {
    public DiscoveryService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
