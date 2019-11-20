/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pakdev.samplefcm.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import com.pakdev.samplefcm.MainActivity;
import com.pakdev.samplefcm.R;

import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMsgService";
    int smallIcon;

    public MyFirebaseMessagingService() {
    }


    // [END receive_message]

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
              //  scheduleJob();
            } else {
                // Handle message within 10 seconds
               // handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

        }









    private void sendNotification(String title,String message) {
        String GROUP_KEY_ALERTS = "com.app.main.ALERTS";


        Intent intent ;

smallIcon= R.drawable.ic_launcher_background;
        NotificationClass nc = new NotificationClass();

        int notifyID =  new Random().nextInt(5000);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

         intent = new Intent(getApplicationContext(), MainActivity.class);
         intent.putExtra("data_noti","notification");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Creating Channel
            nc.createMainNotificationChannel(getApplicationContext());
            //building Notification.

            Notification.Builder notifi = new Notification.Builder(getApplicationContext(), nc.getMainNotificationId());

            notifi.setSmallIcon(smallIcon);
            notifi.setContentTitle(title);
            notifi.setContentText(message);
            notifi.setAutoCancel(true);
            notifi.setContentIntent(pendingIntent);
            notifi.setGroup(GROUP_KEY_ALERTS);
            notifi.setChannelId( nc.getMainNotificationId());
            notifi.setStyle(new Notification.BigTextStyle().bigText(message));
            notifi.setSound(alarmSound, AudioAttributes.USAGE_NOTIFICATION);


            //getting notification object from notification builder.
            Notification n = notifi.build();

            NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


            mNotificationManager.notify(notifyID, n);


        }
        else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //String GROUP_KEY_ALERTS = "com.tpltrakker.main.ALERTS";

                //for devices less than API Level 26
                Notification notification = new Notification.Builder(getApplicationContext())


                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(smallIcon)
                        .setAutoCancel(true)
                        .setSound(alarmSound)

                        .setStyle(new Notification.BigTextStyle()
                                .bigText(message))
                        .setContentIntent(pendingIntent).setPriority(Notification.PRIORITY_DEFAULT)
                        .setGroup(GROUP_KEY_ALERTS)
                        .build();

                NotificationManager mNotificationManager =
                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

// Issue the notification.
                mNotificationManager.notify(notifyID, notification);

            }
            else{
                // String GROUP_KEY_ALERTS = "com.tpltrakker.main.ALERTS";

                //for devices less than API Level 26
                Notification notification = new Notification.Builder(getApplicationContext())


                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(smallIcon)
                        .setAutoCancel(true)
                        .setSound(alarmSound)

                        .setStyle(new Notification.BigTextStyle()
                                .bigText(message))
                        .setContentIntent(pendingIntent).setPriority(Notification.PRIORITY_DEFAULT)
                        //.setGroup(GROUP_KEY_ALERTS)
                        .build();

                NotificationManager mNotificationManager =
                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

// Issue the notification.
                mNotificationManager.notify(notifyID, notification);
            }


        }

    }
}
