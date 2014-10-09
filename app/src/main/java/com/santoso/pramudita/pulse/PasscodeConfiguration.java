package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.santoso.pramudita.pulse.WebService.ChangePasscode;


public class PasscodeConfiguration extends Activity {
    SharedPreferences prefs;
    String passcode;
    EditText edOld,edNew,edNew2;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode_configuration);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        ctx = this;
        prefs = getSharedPreferences("PULSE", Context.MODE_PRIVATE);
        passcode = prefs.getString("passcode","0000");
        edOld = (EditText) findViewById(R.id.edOld);
        edNew = (EditText) findViewById(R.id.edNew);
        edNew2 = (EditText) findViewById(R.id.edNew2);
        edOld.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_NEXT){
                    edNew.requestFocus();
                }
                return false;
            }
        });
        edNew.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_NEXT){
                    edNew2.requestFocus();
                }
                return false;
            }
        });
        edNew2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String old,new1,new2;
                    old = edOld.getText().toString();
                    new1 = edNew.getText().toString();
                    new2 = edNew2.getText().toString();
                    if(old.length()<4 || new1.length()<4 || new2.length()<4){
                        Toast.makeText(ctx, "Passcode length has to be 4 digits", Toast.LENGTH_SHORT).show();
                    }
                    else if(!old.equals(passcode)){
                        Toast.makeText(ctx, "Old passcode is wrong", Toast.LENGTH_SHORT).show();
                    }else if(!new1.equals(new2)){
                        Toast.makeText(ctx, "Confirmation does not match", Toast.LENGTH_SHORT).show();
                    }else if(!old.equals(new1)) {
                        Toast.makeText(ctx, "New passcode is the same as old one", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ctx, "Passcode has been changed successfully!", Toast.LENGTH_SHORT).show();
                        //change passcode
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("passcode",new1);
                        editor.commit();
                        new ChangePasscode(ctx).execute(new1);
                        finish();
                    }
                }
                return false;
            }
        });
        edOld.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edOld.setHint("");
                }else{
                    edOld.setHint("Old");
                }

            }
        });
        edNew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edNew.setHint("");
                }else{
                    edNew.setHint("New");
                }

            }
        });
        edNew2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edNew2.setHint("");
                }else{
                    edNew2.setHint("Confirm");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.passcode_configuration, menu);
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
