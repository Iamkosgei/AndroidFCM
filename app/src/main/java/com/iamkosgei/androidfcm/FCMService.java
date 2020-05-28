package com.iamkosgei.androidfcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "MainActivity";

    public static final String NOTIFICATION_CHANNEL_NAME = "TEST CHANNEL NAME";
    public static final String NOTIFICATION_CHANNEL_ID = "123";
    public static final String NOTIFICATION_CHANNEL_DESCRIPTION = "TEST CHANNEL DESCRIPTION";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.w(TAG, remoteMessage.getNotification().getBody());
        showNotifications(remoteMessage);
    }

    void showNotifications(RemoteMessage remoteMessage){
        NotificationManager notificationManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification= new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_EVENT)
                .setColorized(true)
                .setColor(getResources().getColor(R.color.purple_200))
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(0, notification);
    }


}
