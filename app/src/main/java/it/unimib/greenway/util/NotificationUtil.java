package it.unimib.greenway.util;

import static it.unimib.greenway.util.Constants.CHANNEL_DESCRIPTION;
import static it.unimib.greenway.util.Constants.CHANNEL_ID;
import static it.unimib.greenway.util.Constants.CHANNEL_NAME;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import it.unimib.greenway.R;

public class NotificationUtil {


    public static void showNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Controlla se il dispositivo è stato aggiornato a Android 8.0 (API livello 26) o successivo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Mostra la notifica
        notificationManager.notify(1, builder.build());
    }
}
