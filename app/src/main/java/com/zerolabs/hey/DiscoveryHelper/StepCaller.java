package com.zerolabs.hey.DiscoveryHelper;

/**
 * Created by march_000 on 06.09.2014.
 */
public class StepCaller implements Runnable{

    public StepCaller(Runnable runnable, int step){
        this.runnable = runnable;
        this.step = step;

        mHandler = new android.os.Handler();
        mHandler.postDelayed(this, step);
    }

    Runnable runnable;
    int step;

    public Boolean isRunning = true;

    android.os.Handler mHandler;

    @Override
    public void run() {
        if(isRunning) {
            runnable.run();
            mHandler.postDelayed(this, step);
        }
    }
}

