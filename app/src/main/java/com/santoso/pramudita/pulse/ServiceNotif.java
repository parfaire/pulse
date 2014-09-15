package com.santoso.pramudita.pulse;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Gembloth on 9/8/2014.
 */
public class ServiceNotif extends Service {
    private NotificationManager notificationManager;
    private int NOTIFICATION = 1; //Any unique number for this notification
    Notification.Builder builder = new Notification.Builder(this)
            .setAutoCancel(false)
            .setOngoing(true);
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        //Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        ss();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(NOTIFICATION);
        //Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    private void ss(){
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = android.R.drawable.stat_sys_headset;
        CharSequence tickerText = "Earphone service active!";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        Context context = getApplicationContext();
        CharSequence contentTitle = "Pulse";
        CharSequence contentText = "Earphone!";
        Intent notificationIntent = new Intent(this, SendNotif.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent);
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(NOTIFICATION, notification);
    }
}
