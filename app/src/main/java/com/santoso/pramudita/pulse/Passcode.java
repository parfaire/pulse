package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Passcode extends Activity {
    EditText edPassword;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnDel,btnGo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        edPassword = (EditText) findViewById(R.id.edPassword);
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
                Intent i = new Intent(getApplicationContext(), ServiceNotif.class);
                stopService(i);
                Intent returnIntent = new Intent(getApplicationContext(), ServiceNotif.class);
                setResult(RESULT_OK,returnIntent);
                finish();
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
