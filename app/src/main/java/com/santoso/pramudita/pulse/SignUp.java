package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;


public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        DatePicker dp = (DatePicker)findViewById(R.id.datePicker);
        ImageView img = (ImageView)findViewById(R.id.ivPhoto);
        img.setImageResource(R.drawable.profile_picture);
        EditText edFirstName = (EditText)findViewById(R.id.edFirstname);
        EditText edSurName = (EditText)findViewById(R.id.edSurname);
        EditText edMobileNumber = (EditText)findViewById(R.id.edMobileNumber);
        EditText edVerificationNumber = (EditText)findViewById(R.id.edVerificationNumber);
        EditText edAddress = (EditText)findViewById(R.id.edAddress);
        EditText edSuburb = (EditText)findViewById(R.id.edSuburb);
        Button btnChoosePhoto = (Button)findViewById(R.id.btnChoosePhoto);
        Button btnTakePhoto = (Button)findViewById(R.id.btnTakePhoto);
        Button btnNext = (Button)findViewById(R.id.btnNext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
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
