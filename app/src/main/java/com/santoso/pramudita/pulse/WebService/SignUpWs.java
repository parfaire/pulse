package com.santoso.pramudita.pulse.WebService;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Vincent on 10/14/2014.
 */
public class SignUpWs extends AsyncTask<String, Void, String> {
    private String photo, password, passcode, fname, sname, email, gender,mobile, address, suburb, state, country, postcode, emergencyContact, emergencyNumber;
    private Context context;
    private Date dob;
    public SignUpWs(Context context,Date dob){
        this.context = context; this.dob=dob;
    }
    @Override
    protected String doInBackground(String... arg0){
        try{
            fname = arg0[0];
            sname = arg0[1];
            email = arg0[2];
            gender = arg0[3];
            mobile = arg0[4];
            address = arg0[5];
            suburb = arg0[6];
            state = arg0[7];
            country = arg0[8];
            postcode = arg0[9];
            emergencyContact = arg0[10];
            emergencyNumber = arg0[11];
            photo = arg0[12];
            password = arg0[13];
            passcode = arg0[14];
            String link = Connection.url + "/signup.php";
            URL url = new URL(link);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/d");
            String data = URLEncoder.encode("fname", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8");
            data += "&" + URLEncoder.encode("sname", "UTF-8") + "=" + URLEncoder.encode(sname, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");
            data += "&" + URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(sdf.format(dob), "UTF-8");
            data += "&" + URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8");
            data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
            data += "&" + URLEncoder.encode("suburb", "UTF-8") + "=" + URLEncoder.encode(suburb, "UTF-8");
            data += "&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(state, "UTF-8");
            data += "&" + URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(country, "UTF-8");
            data += "&" + URLEncoder.encode("postcode", "UTF-8") + "=" + URLEncoder.encode(postcode, "UTF-8");
            data += "&" + URLEncoder.encode("emergencyContact", "UTF-8") + "=" + URLEncoder.encode(emergencyContact, "UTF-8");
            data += "&" + URLEncoder.encode("emergencyNumber", "UTF-8") + "=" + URLEncoder.encode(emergencyNumber, "UTF-8");
            data += "&" + URLEncoder.encode("photo", "UTF-8") + "=" + URLEncoder.encode(photo, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("passcode", "UTF-8") + "=" + URLEncoder.encode(passcode, "UTF-8");
            Log.e("data",data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line);
                break;
            }


        }
        catch (Exception e){
        }
        return null;
    }
}
