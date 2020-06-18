package com.shoppingbag.utilities.notification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.shoppingbag.app.AppConfig;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.shopping.fragment.Dashboard;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFireBaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        storeRegIdInPref(s);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.apply();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            //Log.e(TAG, " NOTIFICATON");
            LoggerUtil.logItem(remoteMessage.getNotification().getBody());
            LoggerUtil.logItem(remoteMessage.getData());
        }
        try {
            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                //Log.e(TAG, " DATA");
                LoggerUtil.logItem(remoteMessage.getData());

                JSONObject json = new JSONObject(remoteMessage.getData());
                handleDataMessage(json);

            } else {
                Intent resultIntent = new Intent(getApplicationContext(), Dashboard.class);
                resultIntent.putExtra("message", remoteMessage.getNotification().getBody());
                showNotificationMessage(getApplicationContext(), remoteMessage.getNotification().getBody(), "Dreamy", String.valueOf(System.currentTimeMillis()), resultIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleDataMessage(JSONObject json) {
        try {

//            {"body":"test",
//            "image":"http://www.cashbag.co.in/Images/NotificationImages/877104-b1.png",
//            "title":"Testing"}

            String title = json.getString("title");
            String message = json.getString("body");
            String image = "";
            if (!json.isNull("image")) {
                image = json.getString("image");
            }

//            image = "https://www.cashbag.co.in/Images/NotificationImages/877104-b1.png";
            String timestamp = "" + System.currentTimeMillis();

            //Log.e(TAG, "title: " + title);
            //Log.e(TAG, "message: " + message);


            if (NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                LoggerUtil.logItem("background");
                // app is in foreground, broadcast the push message
                Intent resultIntent = new Intent(getApplicationContext(), Dashboard.class);
                resultIntent.putExtra("message", message);
                if (image.equalsIgnoreCase("")) {
                    showNotificationMessage(getApplicationContext(), message, title, timestamp, resultIntent);
                } else {
                    showNotificationMessageWithBigImage(getApplicationContext(), message, title, timestamp, resultIntent, image);
                }
            } else {
                LoggerUtil.logItem("closed");
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), Dashboard.class);
                resultIntent.putExtra("message", message);
                if (image.equalsIgnoreCase("")) {
                    showNotificationMessage(getApplicationContext(), message, title, timestamp, resultIntent);
                } else {
                    showNotificationMessageWithBigImage(getApplicationContext(), message, title, timestamp, resultIntent, image);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String message, String title, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String message, String title, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}