package com.santoso.pramudita.pulse.Background;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.santoso.pramudita.pulse.Countdown;
import com.santoso.pramudita.pulse.Earphone;
import com.santoso.pramudita.pulse.WebService.ActiveEmergency;

/**
 * Created by Gembloth on 9/8/2014.
 */
public class EarphoneService extends Service {
    private String trigger;
    private LocationManager lm;
    private boolean isGPSEnabled,isNetworkEnabled;
    private double lat,lng;
    private boolean flag = false;
    private MusicIntentReceiver myReceiver = new MusicIntentReceiver();
    private NotificationManager notificationManager;
    private int NOTIFICATION = 1; //Any unique number for this notification
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        trigger="EARPHONE";
        ss();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, filter);
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(NOTIFICATION);
        unregisterReceiver(myReceiver);
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
        CharSequence contentText = "Service of earphones trigger is activated.";
        Intent notificationIntent = new Intent(this,Earphone.class); //add android:launchMode="singleTask" in manifest to not creating new activity
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent);
        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        notificationManager.notify(NOTIFICATION, notification);
    }

    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        try{
                            Earphone.setStatus("Please plug in\nyour headphones",false);
                        }catch(Exception e){

                        }
                        if(flag){
                            Intent i = new Intent(getApplicationContext(),Countdown.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("trigger", trigger);
                            startActivity(i);
                            flag=false;
                        }
                        break;
                    case 1:
                        try{
                            // getting GPS & Network status
                            lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                            isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                            isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                            if (isNetworkEnabled){
                                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                            }else if(isGPSEnabled){
                                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                            }
                            Earphone.setStatus("Your headphones are\nalready plugged in",true);
                        }catch(Exception e){

                        }
                        flag=true;
                        break;
                }
            }
        }
    }
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            lng = location.getLongitude();
            lat = location.getLatitude();
            lm.removeUpdates(this);
            //NOTIFY THE CALL CENTER
            new ActiveEmergency(getApplicationContext()).execute(lat + "", lng + "", trigger);
        }
        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }
    };
}
