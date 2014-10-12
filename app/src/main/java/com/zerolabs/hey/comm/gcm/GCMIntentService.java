package com.zerolabs.hey.comm.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.zerolabs.hey.MainActivity;
import com.zerolabs.hey.R;
import com.zerolabs.hey.model.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jpedro on 11.10.14.
 */
public class GCMIntentService extends IntentService {

    private static String LOG_TAG = GCMIntentService.class.getSimpleName();

    private LocalBroadcastManager broadcaster;

    public static final int NOTIFICATION_ID = 42;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    static final public String TALK_RESULT = "com.zerolabs.hey.comm.gcm.GCMIntentService.talk.REQUEST_PROCESSED";
    static final public String TALK_MESSAGE = "talk_message";

    static final public String HEY_RESULT = "com.zerolabs.hey.comm.gcm.GCMIntentService.hey.REQUEST_PROCESSED";
    static final public String HEY_MESSAGE = "hey_message";

    public GCMIntentService() {
        super("GcmIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    public void sendTalkResult(Talk talk) {
        Intent intent = new Intent(TALK_RESULT);
        if (talk != null)
            intent.putExtra(TALK_MESSAGE, talk.getBundle());
        broadcaster.sendBroadcast(intent);
    }

    public void sendHeyResult(Hey hey) {
        Intent intent = new Intent(HEY_RESULT);
        if(hey != null)
            intent.putExtra(HEY_MESSAGE, hey.getBundle());
        broadcaster.sendBroadcast(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.d(LOG_TAG, "Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.d(LOG_TAG, "Deleted messages on server: " +
                        extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                // Post notification of received message.
                Log.d(LOG_TAG, "Received: " + extras.toString());

                // This loop represents the service doing some work.
                if (extras.getString(Hey.KEY_IS_HEY).equals("1")) {
                    Hey hey = new Hey(extras);

                    Log.d(LOG_TAG, "That was a hey!!");

                    Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(500);

                    receiveHey(hey);
                    sendHeyResult(hey);

                } else {
                    Talk talk = new Talk(extras);
                    Log.d(LOG_TAG, "Received a Talk, will forward it to MeetActivity");
                    sendTalkResult(talk);
                }

                Log.i(LOG_TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }


    private void receiveHey(Hey hey) {

        String FILENAME ="incomingHey.txt";
        String strMsgToSave = hey.getSender().getUserId();
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            try
            {
                fos.write( strMsgToSave.getBytes() );
                fos.close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }





        User sender = hey.getSender();

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent contentIntent = new Intent(this, MainActivity.class);
        contentIntent.putExtra(MainActivity.KEY_HEY, hey.getBundle());

        String msg = sender.getUsername() + " sent you a hey!";

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("hey!")
                        .setContentText(msg)
                    .setSmallIcon(R.drawable.com_facebook_button_check);

        Log.d(LOG_TAG, "A notification should pop up!");

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // the application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(contentIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}

