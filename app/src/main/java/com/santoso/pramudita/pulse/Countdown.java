package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class Countdown extends Activity {
    MyCount timerCount;
    Animation animation;
    TextView tvCount;
    Button btnCancel;
    String trigger;
    float fulltextsize,textsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_countdown);
        textsize=fulltextsize=250;
        tvCount = (TextView) findViewById(R.id.tvCount);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerCount.cancel();
                animation.cancel();
                finish();
            }
        });
        trigger = getIntent().getStringExtra("trigger");
        animation = new Animation(5500, 20);
        timerCount = new MyCount(5500, 1000);
        animation.start();
        timerCount.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.countdown, menu);
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

    //timer classes
    public class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish(){
            Intent i = new Intent(getApplicationContext(),SendNotif.class);
            i.putExtra("trigger",trigger);
            startActivity(i);
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textsize=fulltextsize;
            tvCount.setText(""+ (millisUntilFinished/1000));
            tvCount.setTextSize(TypedValue.COMPLEX_UNIT_SP,fulltextsize);
        }
    }
    public class Animation extends CountDownTimer {
        public Animation(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish(){
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textsize=textsize-2;
            tvCount.setTextSize(TypedValue.COMPLEX_UNIT_SP,textsize-10);
        }
    }

}
