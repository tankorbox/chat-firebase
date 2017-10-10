package com.example.tan.finalproject.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.activities.ChatActivity;
import com.example.tan.finalproject.activities.MainActivity;

import static com.example.tan.finalproject.common.Constants.NOTIFY_NEW_MESSAGE_ID;

/**
 * Created by Tan on 5/9/2017.
 */

public class NewMessageNotification {
    public static void showNotification(Context context,String name) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("New message from:");
        mBuilder.setContentText(name);
        mBuilder.setTicker("New message from "+name+"!");
        mBuilder.setSmallIcon(R.drawable.chatt_navi);
        mBuilder.setAutoCancel(true);
        //
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
            new VibrateCreator(context).vibrate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Creates an explicit intent for an Activity in your app */
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,(int) System.currentTimeMillis(),resultIntent,0);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify((int)System.currentTimeMillis(),mBuilder.build());
    }
}
