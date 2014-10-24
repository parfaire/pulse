package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;


public class SignUp extends Activity {
    SharedPreferences prefs;
    Context context;
    private RadioButton rButton;
    private int year, month, day;
    private final static int REQUEST_CONTACTPICKER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        prefs = getSharedPreferences("PULSE", Context.MODE_PRIVATE);
        final DatePicker dp = (DatePicker)findViewById(R.id.datePicker);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        dp.init(year, month,day, null);
        ImageView img = (ImageView)findViewById(R.id.ivPhoto);
        img.setImageResource(R.drawable.profile_picture);
        final EditText edFirstName = (EditText)findViewById(R.id.edFirstname);
        final EditText edSurName = (EditText)findViewById(R.id.edSurname);
        final EditText edEmail = (EditText)findViewById(R.id.edEmail);
        final EditText edMobileNumber = (EditText)findViewById(R.id.edMobileNumber);
        EditText edVerificationNumber = (EditText)findViewById(R.id.edVerificationNumber);
        final EditText edEmergencyContact = (EditText)findViewById(R.id.edEmergencyContact);
        final EditText edEmergencyNumber = (EditText)findViewById(R.id.edEmergencyNumber);
        final EditText edAddress = (EditText)findViewById(R.id.edAddress);
        final EditText edSuburb = (EditText)findViewById(R.id.edSuburb);
        final EditText edState = (EditText)findViewById(R.id.edState);
        final EditText edCountry = (EditText)findViewById(R.id.edCountry);
        final EditText edPostcode = (EditText)findViewById(R.id.edPostcode);
        Button btnChoosePhoto = (Button)findViewById(R.id.btnChoosePhoto);
        Button btnTakePhoto = (Button)findViewById(R.id.btnTakePhoto);
        Button btnNext = (Button)findViewById(R.id.btnNext);
        final RadioButton rbMale = (RadioButton)findViewById(R.id.rbMale);
        RadioButton rbFemale = (RadioButton)findViewById(R.id.rbFemale);
        final RadioGroup rGroup = (RadioGroup)findViewById(R.id.radioGroup);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to camera and take photo
                }
            });

        btnChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to gallery and select picture
            }
        });

        edEmergencyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACTPICKER);
            }
        });

//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data){
//            super.onActivityResult(requestCode, resultCode, data);
//            if (requestCode == REQUEST_CONTACTPICKER)
//            {
//                if (resultCode == RESULT_OK)
//                {
//                    Uri contentUri = data.getData;
//                    String contactId = contentUri.getLastPathSegment();
//                    Cursor cursor =getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone._ID+="?", new String[]{contactId}, null);
//                    startManagingCursor(cursor);
//                    Boolean numbersExist = cursor.moveToFirst();
//                    int phoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                    String phoneNumber = "";
//                    while (numbersExist)
//                    {
//                        phoneNumber = phoneNumber.trim();
//                        numbersExist = cursor.moveToNext();
//                    }
//                    stopManagingCursor(cursor);
//                    if(!phoneNumber.equals(""))
//                    {
//                        setPhoneNumber(phoneNumber);
//                    }
//                }
//            }
//        }


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to passcode to choose main passcode then confirm passcode and set alternative passcode and confirm alternative passcode

                String fname, sname, email, gender, dob, mobile, address, suburb, state, country, postcode, emergencyContact, emergencyNumber;
                fname = edFirstName.getText().toString();
                sname = edSurName.getText().toString();
                email = edEmail.getText().toString();
                //dob =

                int selectedId = rGroup.getCheckedRadioButtonId();
                rButton = (RadioButton)findViewById(selectedId);

                gender = rButton.getText().toString();
                mobile = edMobileNumber.getText().toString();
                address = edAddress.getText().toString();
                suburb = edSuburb.getText().toString();
                state = edState.getText().toString();
                country = edCountry.getText().toString();
                postcode = edPostcode.getText().toString();
                emergencyContact = edEmergencyContact.getText().toString();
                emergencyNumber = edEmergencyNumber.getText().toString();
                //new SignUp(context).execute(fname, sname, email, gender, dob, mobile, address, suburb, state, country, postcode, emergencyContact, emergencyNumber);
            }
        });
    }

    public SharedPreferences getSharedPreferences(String pulse, int modePrivate) {
        return null;
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
