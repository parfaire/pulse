package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Earphone extends Activity {
    private TextView tvStatus;
    private MusicIntentReceiver myReceiver;
    private boolean flag;
    private Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earphone);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        myReceiver = new MusicIntentReceiver();
        Intent i= new Intent(getApplicationContext(), ServiceNotif.class);
        startService(i);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),Passcode.class);
            startActivityForResult(i,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                finish();
            }
        }
    }

    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        tvStatus.setText("UNPLUGGED");
                        tvStatus.setTextColor(Color.RED);
                        if(flag){
                            Intent i = new Intent(getApplicationContext(),Countdown.class);
                            startActivity(i);
                            flag=false;
                        }
                        break;
                    case 1:
                        tvStatus.setText("PLUGGED");
                        tvStatus.setTextColor(Color.GREEN);
                        flag=true;
                        break;
                    default:
                        tvStatus.setText("-");
                        tvStatus.setTextColor(Color.BLACK);
                        flag=false;
                }
            }
        }
    }

    @Override public void onResume() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, filter);
        super.onResume();
    }

    @Override public void onPause() {
        unregisterReceiver(myReceiver);
        super.onPause();
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
}
