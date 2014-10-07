package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class SignupMenu extends Activity {
    Button btnSUM;
    Button btnSUSE;
    Intent i;
    private static final int CONTACT_PICKER_RESULT = 1001;
    private final int PICK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_menu);
        btnSUM = (Button) findViewById(R.id.btnSUM);
        btnSUSE = (Button) findViewById(R.id.btnSUSE);

        btnSUM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
            }
        });
        btnSUSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, Phone.CONTENT_URI);
                startActivityForResult(intent, CONTACT_PICKER_RESULT);
                //i = new Intent(getApplicationContext(), SignUp.class);
                //startActivity(i);
            }
        });
    }

        public void onActivityResult(int reqCode, int resultCode, Intent data)
        {
            super.onActivityResult(reqCode,resultCode, data);
            if(resultCode == RESULT_OK){
                switch (reqCode){
                    case CONTACT_PICKER_RESULT:
                        Cursor cursor = null;
                        String number = "";
                        String firstName = "";
                        String lastName = "";
                        try{
                            Uri result = data.getData();

                            String id= result.getLastPathSegment();

                            cursor = getContentResolver().query(Phone.CONTENT_URI,null, Phone._ID + " = ? ", new String[]{id},null);
                            int numberIdx = cursor.getColumnIndex(Phone.DATA);
                            if(cursor.moveToFirst()){
                                number = cursor.getString((numberIdx));
                                firstName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                                lastName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                            }
                            else{
                                // failed
                            }
                        } catch (Exception e){

                        } finally{
                            if (cursor != null){
                                cursor.close();
                            }
                        }
                        Log.d("TAG", number);
                        Log.d("TAG", firstName);
                        Log.d("TAG", lastName);
                }
            }
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signup_menu, menu);
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
