package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santoso.pramudita.pulse.WebService.Login;


public class Cover extends Activity {
    SharedPreferences prefs;
    Button btnLogin,btnHome,btnLogout;
    TextView tvSignUp,tvPasscode,tvInfo,tvLine;
    EditText edEmail,edPassword;
    LinearLayout container,containerlogin;
    Context ctx;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        ctx=this;
        prefs = getSharedPreferences("PULSE", Context.MODE_PRIVATE);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        tvPasscode = (TextView) findViewById(R.id.tvPasscode);
        tvLine = (TextView) findViewById(R.id.tvLine);
        tvInfo  = (TextView) findViewById(R.id.tvInfo);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        container = (LinearLayout) findViewById(R.id.container);
        containerlogin = (LinearLayout) findViewById(R.id.containerlogin);

        tvLine.setVisibility(View.GONE);
        tvPasscode.setVisibility(View.GONE);

        edEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_NEXT){
                    edPassword.requestFocus();
                }
                return false;
            }
        });
        edPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    btnLogin.performClick();
                }
                return false;
            }
        });
        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edEmail.setHint("");
                }else{
                    edEmail.setHint("Email Address");
                }

            }
        });
        edPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edPassword.setHint("");
                }else{
                    edPassword.setHint("Password");
                }

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ctx, MainMenu.class);
                startActivity(i);
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ctx, SignupMenu.class);
                startActivity(i);
            }
        });
        tvPasscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ctx, PasscodeConfiguration.class);
                startActivity(i);
            }
        });
        //preferences
        if (prefs.contains("email")){
            edEmail.setText(prefs.getString("email",""));
            if (prefs.contains("password")){
                edPassword.setText(prefs.getString("password",""));
                btnLogin.performClick();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cover, menu);
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
    private void login() {
        try {
            String un, pw;
            un = edEmail.getText().toString();
            pw = edPassword.getText().toString();
            new Login(ctx).execute(un, pw);
        }catch(Exception e){}
    }
    public void changeUIwhenLogin(){
        tvInfo.setText("Welcome, "+edEmail.getText().toString());
        tvLine.setVisibility(View.VISIBLE);
        tvPasscode.setVisibility(View.VISIBLE);
        containerlogin.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
    }

    private void logout() {
        tvLine.setVisibility(View.GONE);
        tvPasscode.setVisibility(View.GONE);
        containerlogin.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        edEmail.setText("");
        edPassword.setText("");
        prefs.edit().clear().commit();
    }

}
