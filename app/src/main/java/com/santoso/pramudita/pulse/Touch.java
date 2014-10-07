package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.santoso.pramudita.pulse.WebService.ActiveEmergency;
import com.santoso.pramudita.pulse.WebService.StopEmergency;


public class Touch extends Activity {
    private SharedPreferences prefs;
    private boolean flagCancel;
    private Button btnCancel;
    private TextView tvStatus;
    private FrameLayout frameLayout;
    private Intent i;
    private Context ctx;
    private LocationManager lm;
    private boolean isGPSEnabled,isNetworkEnabled;
    private double lat,lng;
    private String trigger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_touch);
        prefs = getSharedPreferences("PULSE",Context.MODE_PRIVATE);
        trigger="TOUCH";
        flagCancel=false;
        ctx=this;
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagCancel) {
                    new StopEmergency(ctx).execute(prefs.getString("logid", ""), "YES");
                }
                finish();
            }
        });
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // getting GPS & Network status
                        flagCancel=true;
                        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                        isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                        if (isNetworkEnabled){
                            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                        }else if(isGPSEnabled){
                            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        }
                        tvStatus.setText("Once you release the notification will be sent");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        i = new Intent(getApplicationContext(),Countdown.class);
                        i.putExtra("trigger",trigger);
                        startActivity(i);
                        finish();
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.touch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
    }
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            lng = location.getLongitude();
            lat = location.getLatitude();
            lm.removeUpdates(this);
            //NOTIFY THE CALL CENTER
            new ActiveEmergency(ctx).execute(lat+"",lng+"",trigger);
        }
        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }
    };
}
