package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.santoso.pramudita.pulse.Background.EarphoneService;
import com.santoso.pramudita.pulse.WebService.StopEmergency;


public class Earphone extends Activity {
    private static TextView tvStatus;
    private static boolean flagCancel=false;
    private SharedPreferences prefs;
    private Button btnCancel;
    private Intent i;
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_earphone);
        ctx=this;
        prefs = getSharedPreferences("PULSE",Context.MODE_PRIVATE);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        i= new Intent(getApplicationContext(), EarphoneService.class);
        startService(i);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, EarphoneService.class);
                stopService(i);
                if(flagCancel){
                    new StopEmergency(ctx).execute(prefs.getString("logid", ""), "YES");
                }
                finish();
            }
        });
    }
    public static void setStatus(String s,boolean f){
        tvStatus.setText(s); flagCancel=f;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.earphone, menu);
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
