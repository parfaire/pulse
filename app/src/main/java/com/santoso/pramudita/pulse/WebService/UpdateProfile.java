package com.santoso.pramudita.pulse.WebService;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Vincent on 10/13/2014.
 */
public class UpdateProfile extends AsyncTask<String, Void, String> {
    private Context context;
    private String fname, sname, dob, mobile, email, passcode, address, emergencyContact, emergencyNumber;
    public UpdateProfile(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... arg0) {
        try {

        }
        catch(Exception e){

        }
        return null;
    }


}
