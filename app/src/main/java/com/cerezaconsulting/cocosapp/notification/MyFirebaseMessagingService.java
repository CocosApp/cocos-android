package com.cerezaconsulting.cocosapp.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.presentation.main.restaurante.RestaurantActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by kath on 02/01/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private boolean sound;
    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "¡Mensaje recibido!");
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String, String> data = remoteMessage.getData();
        String body = remoteMessage.getData().get("body");
        String title = remoteMessage.getData().get("title");
       // Integer restaurant_id = Integer.parseInt(data.get("restaurant_id"));
         Integer restaurant_id = Integer.parseInt(remoteMessage.getData().get("restaurant_id"));
        String restaurant_name = remoteMessage.getData().get("restaurant_name");

        RestauranteResponse restauranteResponse = new RestauranteResponse();
        restauranteResponse.setId(restaurant_id);
        restauranteResponse.setName(restaurant_name);

        Bundle bundle = new Bundle();
        bundle.putSerializable("restEntity", restauranteResponse);

        displayNotification( body, title, restauranteResponse);
        //startActivity(new Intent(this, RestaurantActivity.class).putExtra("restEntity", bundle).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }



    private void sendNotification(String messageBody) {
    /*  //  Intent intent = new Intent(this, PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

      //  String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());*/
    }

    private void pushNotification(RemoteMessage remoteMessage) {


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
      /*  try {
            Bitmap bitmap = Ion.with(this).load(remoteMessage.getNotification().getIcon()).asBitmap().get();
            notificationBuilder.setLargeIcon(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        if (sound)
            notificationBuilder.setSound(defaultSoundUri);

        sound = false;

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(remoteMessage.getNotification().getBody());
        inboxStyle.addLine(new JSONObject(remoteMessage.getData()).toString());

        notificationBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());

        notificationBuilder.setStyle(inboxStyle);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());

    }



  private void displayNotification(String message, String title, RestauranteResponse restauranteResponse) {
      Intent intent = new Intent(this, RestaurantActivity.class);
      intent.putExtra("restEntity", restauranteResponse);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent,
              PendingIntent.FLAG_ONE_SHOT);

      Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
