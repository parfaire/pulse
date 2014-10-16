package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.santoso.pramudita.pulse.Background.EarphoneService;
import com.santoso.pramudita.pulse.Background.LocationService;
import com.santoso.pramudita.pulse.WebService.StartEmergency;

import java.io.IOException;
import java.util.Date;


public class SendNotif extends Activity implements SurfaceHolder.Callback  {
    private SharedPreferences prefs;
    private Button btnCancel;
    private String trigger;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private MediaRecorder mrec = new MediaRecorder();
    private Camera mCamera;
    public static MarkerOptions mOpt;
    public static Marker marker;
    public static GoogleMap gMap;
    public static LocationManager lm;
    private Context ctx;
    private boolean isGPSEnabled,isNetworkEnabled;
    private double lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_send_notif);
        ctx=this;

        //set cancel listener
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx,Passcode.class);
                startActivityForResult(i, 1);
            }
        });

        //stop earphone service if the trigger is service
        try{
            trigger = getIntent().getStringExtra("trigger");
            if (trigger.equals("EARPHONE")) {
                //Stop earphone service
                Intent i = new Intent(getApplicationContext(), EarphoneService.class);
                stopService(i);
            }
        }catch(Exception e){
                e.printStackTrace();
        }

        //SET THE MAP
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        // getting GPS & Network status
        isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled){
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }else if(isGPSEnabled){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        //RECORDING
        /*try {
            mCamera = Camera.open();
            surfaceView = (SurfaceView) findViewById(R.id.surface_camera);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }catch(Exception e){}*/
    }
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            lng = location.getLongitude();
            lat = location.getLatitude();
            LatLng currentPlace = new LatLng(lat,lng);
            mOpt = new MarkerOptions().position(currentPlace);
            marker = gMap.addMarker(mOpt);
            marker.setPosition(currentPlace);
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPlace,15));
            lm.removeUpdates(this);
            //NOTIFY THE CALL CENTER and get the ID
            prefs = getSharedPreferences("PULSE",Context.MODE_PRIVATE);
            String logid = prefs.getString("logid","");
            //when the activity is recreate dont send the notif again by checking if the service isnt already running
            if(!isMyServiceRunning(LocationService.class)) {
                new StartEmergency(ctx).execute(lat + "", lng + "", logid);
                Intent i = new Intent(ctx, LocationService.class);
                i.putExtra("lat", lat + "");
                i.putExtra("lng", lng + "");
                i.putExtra("logid", logid);
                startService(i);
            }
        }
        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }
    };
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera != null){
           Camera.Parameters params = mCamera.getParameters();
           mCamera.setParameters(params);
           try{startRecording();
               Timer timer = new Timer(60000, 1000);
           }catch(Exception e){}
        }
        else {
            Toast.makeText(getApplicationContext(), "Camera not available!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopRecording();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                if (trigger.equals("EARPHONE")) {
                    Intent i = new Intent(getApplicationContext(), EarphoneService.class);
                    startService(i);
                }
                Intent i = new Intent(getApplicationContext(), LocationService.class);
                stopService(i);
                finish();
            }
        }catch (Exception e){
            Intent i = new Intent(getApplicationContext(), LocationService.class);
            stopService(i);
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finish();
        }
    }

    protected void startRecording() throws IOException
    {
        mrec = new MediaRecorder();  // Works well
        mCamera.setDisplayOrientation(90);
        mCamera.unlock();

        mrec.setCamera(mCamera);

        mrec.setPreviewDisplay(surfaceHolder.getSurface());
        mrec.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mrec.setAudioSource(MediaRecorder.AudioSource.MIC);

        mrec.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mrec.setPreviewDisplay(surfaceHolder.getSurface());
        mrec.setOutputFile("/sdcard/Pulse_"+ DateFormat.format("yyyy-MM-dd_kk-mm-ss", new Date().getTime())+".mp4");

        mrec.prepare();
        mrec.start();
    }

    protected void stopRecording() {
        mrec.stop();
        mrec.release();
        mCamera.lock();
        mCamera.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.send_notif, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stopRecording();
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

    public class Timer extends CountDownTimer {
        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish(){
            stopRecording();
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
