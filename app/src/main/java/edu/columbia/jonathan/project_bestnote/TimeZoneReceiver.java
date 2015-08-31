package edu.columbia.jonathan.project_bestnote;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Jonathan on 5/11/15.
 */
public class TimeZoneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent i) {
        //Do something when your intent is received
        showNotification(ctx);
    }

    public void showNotification(Context ctx) {
        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, MainActivity.class), 0);

        CharSequence contentTitle = ctx.getString(R.string.timeZoneChanged);
        CharSequence contentText = ctx.getString(R.string.goToApp);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.ideaiconsmall)
                        .setContentTitle(contentTitle)
                        .setContentText(contentText);
        mBuilder.setContentIntent(contentIntent);
//        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

}
