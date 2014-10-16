package com.santoso.pramudita.pulse.Background;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.santoso.pramudita.pulse.R;
import com.santoso.pramudita.pulse.SendNotif;
import com.santoso.pramudita.pulse.WebService.StopEmergency;
import com.santoso.pramudita.pulse.WebService.UpdateEmergencyLocation;

/**
 * Created by Gembloth on 9/8/2014.
 */
public class LocationService extends Service {
    private String lat,lng,logid;
    private NotificationManager notificationManager;
    private LocationManager lm;
    private boolean isGPSEnabled,isNetworkEnabled;
    private int NOTIFICATION = 2; //Any unique number for this notification
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        try {
            ss();
            lat = intent.getStringExtra("lat");
            lng = intent.getStringExtra("lng");
            logid = intent.getStringExtra("logid");
            //getting GPS & Network status & update location
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isNetworkEnabled) {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            } else if (isGPSEnabled) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
            /*timer = new Timer(60 * 60 * 1000, 60000);
            timer.start();*/
            return Service.START_STICKY;
        }catch(Exception e){
            e.printStackTrace();
        }
            return Service.START_STICKY;
    }
    @Override
    public void onDestroy() {
        Log.e("LocationService","STOPPED");
        super.onDestroy();
        if(lm!=null) {
            lm.removeUpdates(locationListener);
        }
        new StopEmergency(getApplicationContext()).execute(logid,"no");
        notificationManager.cancel(NOTIFICATION);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    private void ss(){
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_pulse;
        CharSequence tickerText = "Location service active!";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        Context context = getApplicationContext();
        CharSequence contentTitle = "Pulse";
        CharSequence contentText = "Tracking your location.";
        Intent notificationIntent = new Intent(this,SendNotif.class); //add android:launchMode="singleTask" in manifest to not creating new activity
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent);
        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        notificationManager.notify(NOTIFICATION, notification);
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.e("LocListener","Location has been updated");
            lng = location.getLongitude()+"";
            lat = location.getLatitude()+"";
            LatLng currentPlace = new LatLng(location.getLatitude(),location.getLongitude());
            SendNotif.marker.setPosition(currentPlace);
            SendNotif.gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPlace, 15));
            Log.e("LocationService","LocationListener is running");
            new UpdateEmergencyLocation(getApplicationContext()).execute(lat,lng,logid);
        }
        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }
    };
}
