package com.santoso.pramudita.pulse;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.net.URI;


public class SignupMenu extends Activity {
    Button btnSUM;
    Button btnSUSE;
    Intent i;
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
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK);
                //i = new Intent(getApplicationContext(), SignUp.class);
                //startActivity(i);
            }
        });

//        @Override
//        public void onActivityResult(int reqCode, final int resultCode, Intent data)
//        {
//            super.onActivityResult(reqCode,resultCode, data);
//            switch (reqCode) {
//                case (PICK) :
//                    if (resultCode == Activity.RESULT_OK) {
//                        URI contactData = data.getData();
//                        Cursor c =  managedQuery(contactData, null, null, null, null);
//                        if (c.moveToFirst()) {
//                            String name = c.getString(c.getColumnIndexOrThrow(Contacts.People.NAME));
//                            // TODO Whatever you want to do with the selected contact name.
//                        }
//                    }
//                    break;
//            }
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
