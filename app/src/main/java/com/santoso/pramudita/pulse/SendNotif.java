package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;


public class SendNotif extends Activity implements SurfaceHolder.Callback  {
    private Button btnCancel;
    private String trigger;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    public MediaRecorder mrec = new MediaRecorder();
    private Camera mCamera;
    private double lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_send_notif);
        //set cancel listener
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Passcode.class);
                startActivityForResult(i, 1);
            }
        });
        //stop earphone service if the trigger is service
        trigger = getIntent().getStringExtra("trigger");
        if (trigger.equals("EARPHONE")) {
            //Stop earphone service
            Intent i = new Intent(getApplicationContext(), EarphoneService.class);
            stopService(i);
        }

        //SET THE MAP
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lng = 144.962979600000040000;
        lat = -37.813186900000000000;
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        LatLng currentPlace = new LatLng(lat,lng);
        GoogleMap mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        MarkerOptions a = new MarkerOptions().position(currentPlace);
        Marker m = mMap.addMarker(a);
        m.setPosition(currentPlace);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPlace,15));

        //RECORDING
        try {
            mCamera = Camera.open();
            surfaceView = (SurfaceView) findViewById(R.id.surface_camera);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }catch(Exception e){}

        //NOTIFY THE CALL CENTER
    }
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            lng = location.getLongitude();
            lat = location.getLatitude();
            Toast.makeText(getApplicationContext(),"Latitude:" + lat + ", Longitude:" + lng,Toast.LENGTH_SHORT).show();
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
           try{startRecording();}catch(Exception e){}
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
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                if(trigger.equals("EARPHONE")) {
                    Intent i = new Intent(getApplicationContext(), EarphoneService.class);
                    startService(i);
                }
                finish();
            }
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
}
