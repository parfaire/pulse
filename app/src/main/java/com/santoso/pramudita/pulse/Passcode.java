package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.santoso.pramudita.pulse.Background.EarphoneService;


public class Passcode extends Activity {
    SharedPreferences prefs;
    Intent i;
    String passcode;
    EditText edPassword;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnDel,btnGo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        prefs = getSharedPreferences("PULSE", Context.MODE_PRIVATE);
        passcode = prefs.getString("passcode","0000");
        i=getIntent();
        edPassword = (EditText) findViewById(R.id.edPassword);
        /*edPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //stop send notif service
                    Intent newIn = new Intent(getApplicationContext(), EarphoneService.class);
                    stopService(newIn);
                    setResult(RESULT_OK,i);
                    finish();
                }
                return false;
            }
        });*/
        edPassword.setEnabled(false);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnGo = (Button) findViewById(R.id.btnGo);

        btn1.setOnClickListener(new myClick("1"));
        btn2.setOnClickListener(new myClick("2"));
        btn3.setOnClickListener(new myClick("3"));
        btn4.setOnClickListener(new myClick("4"));
        btn5.setOnClickListener(new myClick("5"));
        btn6.setOnClickListener(new myClick("6"));
        btn7.setOnClickListener(new myClick("7"));
        btn8.setOnClickListener(new myClick("8"));
        btn9.setOnClickListener(new myClick("9"));
        btn0.setOnClickListener(new myClick("0"));
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pcode = edPassword.getText().toString();
                if(passcode.equals(pcode)) {
                    Intent newIn = new Intent(getApplicationContext(), EarphoneService.class);
                    stopService(newIn);
                    Intent returnIntent = new Intent(getApplicationContext(), SendNotif.class);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Passcode is wrong "+passcode,Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edPassword.getText().toString();
                int length = text.length();
                if (length>0) {
                    edPassword.setText(text.substring(0, length - 1));
                }
            }
        });
    }

    private class myClick implements View.OnClickListener{
        String s;
        public myClick(String s){
            this.s=s;
        }
        @Override
        public void onClick(View v) {
            String text = edPassword.getText().toString();
            int length = text.length();
            if(length<4){
                edPassword.append(s);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.passcode, menu);
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
