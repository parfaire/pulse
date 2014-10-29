package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
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

import com.santoso.pramudita.pulse.WebService.SignUpWs;
import com.santoso.pramudita.pulse.WebService.UploadFile;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;


public class SignUp extends Activity {
    private static final int SELECT_PHOTO = 100;
    private Context context;
    private EditText edPassword,edPasscode,edFirstName,edSurName,edEmail,edMobileNumber,edVerificationNumber;
    private EditText edAddress,edSuburb,edState,edCountry,edPostcode,edEmergencyContact,edEmergencyNumber;
    private Button btnChoosePhoto,btnTakePhoto,btnNext;
    private RadioButton rbGender;
    private RadioGroup rGroup;
    private DatePicker dp;
    private ImageView img;
    private int year, month, day;
    private Calendar c;
    private String photo,photoPath;
    private final static int REQUEST_CONTACTPICKER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = this;
        dp = (DatePicker)findViewById(R.id.datePicker);
        c  = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        dp.init(year, month,day, null);
        img = (ImageView)findViewById(R.id.ivPhoto);
        edPassword = (EditText)findViewById(R.id.edPassword);
        edPasscode = (EditText)findViewById(R.id.edPasscode);
        edFirstName = (EditText)findViewById(R.id.edFirstname);
        edSurName = (EditText)findViewById(R.id.edSurname);
        edEmail = (EditText)findViewById(R.id.edEmail);
        edMobileNumber = (EditText)findViewById(R.id.edMobileNumber);
        edVerificationNumber = (EditText)findViewById(R.id.edVerificationNumber);
        edEmergencyContact = (EditText)findViewById(R.id.edEmergencyContact);
        edEmergencyNumber = (EditText)findViewById(R.id.edEmergencyNumber);
        edAddress = (EditText)findViewById(R.id.edAddress);
        edSuburb = (EditText)findViewById(R.id.edSuburb);
        edState = (EditText)findViewById(R.id.edState);
        edCountry = (EditText)findViewById(R.id.edCountry);
        edPostcode = (EditText)findViewById(R.id.edPostcode);
        btnChoosePhoto = (Button)findViewById(R.id.btnChoosePhoto);
        btnTakePhoto = (Button)findViewById(R.id.btnTakePhoto);
        btnNext = (Button)findViewById(R.id.btnNext);
        rGroup = (RadioGroup)findViewById(R.id.radioGroup);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to camera and take photo
                }
            });

        btnChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
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
                try {
                    //go to passcode to choose main passcode then confirm passcode and set alternative passcode and confirm alternative passcode
                    String password, passcode, fname, sname, email, gender, mobile, address, suburb, state, country, postcode, emergencyContact, emergencyNumber;
                    Date dob = c.getTime();
                    password = edPassword.getText().toString();
                    passcode = edPasscode.getText().toString().trim();
                    fname = edFirstName.getText().toString().trim();
                    sname = edSurName.getText().toString().trim();
                    photo = fname+sname+".jpg";
                    email = edEmail.getText().toString().trim();
                    int selectedId = rGroup.getCheckedRadioButtonId();
                    rbGender = (RadioButton) findViewById(selectedId);
                    gender = rbGender.getText().toString().substring(0, 1);
                    mobile = edMobileNumber.getText().toString().trim();
                    address = edAddress.getText().toString().trim();
                    suburb = edSuburb.getText().toString().trim();
                    state = edState.getText().toString().trim();
                    country = edCountry.getText().toString().trim();
                    postcode = edPostcode.getText().toString().trim();
                    emergencyContact = edEmergencyContact.getText().toString().trim();
                    emergencyNumber = edEmergencyNumber.getText().toString().trim();
                    Log.e("A", fname + sname + email + gender + dob + mobile + address + suburb + state + country + postcode + emergencyContact + emergencyNumber);
                    if (password.equals("") || passcode.equals("") || fname.equals("") || sname.equals("") || email.equals("") || gender.equals("") || mobile.equals("") || address.equals("") || suburb.equals("") || state.equals("") || country.equals("") || postcode.equals("") || emergencyContact.equals("") || emergencyNumber.equals("")) {
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        new SignUpWs(context,dob).execute(fname, sname, email, gender, mobile, address, suburb, state, country, postcode, emergencyContact, emergencyNumber, photo, password, passcode);
                        UploadFile u = new UploadFile(photoPath,photo);
                        Toast.makeText(context, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }catch(Exception ex){
                    Log.e("SignUpError",ex.getMessage());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        Uri selectedImage = imageReturnedIntent.getData();
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        img.setImageBitmap(yourSelectedImage);

                        //MEDIA GALLERY
                        photoPath = getPath(selectedImage);

                        Log.e("Path",photoPath);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
        }
    }
    //UPDATED!
    public String getPath(Uri uri) {
        int column_index;
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        //photoPath = cursor.getString(column_index);

        return cursor.getString(column_index);
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
