package com.zerolabs.hey.comm;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by jpedro on 11.10.14.
 */
public class RequestManager {

    /**
     * the queue
     */
    private static RequestQueue mRequestQueue;

    /**
     * Nothing to see here.
     */
    private RequestManager() {
        // no instances
    }

    /**
     * @param context
     * application context
     */
    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * @return
     * instance of the queue
     * @throws
     * IllegalStateException if init has not yet been called
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            return null;
            //throw new IllegalStateException("Not initialized");
        }
    }
}