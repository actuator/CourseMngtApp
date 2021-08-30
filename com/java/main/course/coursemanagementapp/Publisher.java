package com.java.main.course.coursemanagementapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * class to assist publishing of a notification
 */
public class Publisher extends BroadcastReceiver {

    private static final String NOTIFICATION_ID_KEY = "notification-id";
    private static final String NOTIFICATION_KEY = "notification";

    /**
     * Publish the Notification
     *
     * @param context
     * @param intent
     */
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION_KEY);
        int id = intent.getIntExtra(NOTIFICATION_ID_KEY, 0);
        notificationManager.notify(id, notification);
    }

    /**
     * Get the Notification Id Key
     *
     * @return id key
     */
    public static String getIDKey() {
        return NOTIFICATION_ID_KEY;
    }

    /**
     * Get the Notificdaiton Key
     *
     * @return Notification Key
     */
    public static String getKey() {
        return NOTIFICATION_KEY;
    }

}
