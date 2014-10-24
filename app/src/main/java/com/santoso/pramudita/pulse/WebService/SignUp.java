package com.santoso.pramudita.pulse.WebService;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

/**
 * Created by Vincent on 10/14/2014.
 */
public class SignUp extends AsyncTask<String, Void, String> {
    private String fname, sname, email, gender, dob, mobile, address, suburb, state, country, postcode, emergencyContact, emergencyNumber;
    private Context context;
    public SignUp(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... arg0){
        try{
            fname = arg0[0];
            sname = arg0[1];
            email = arg0[2];
            gender = arg0[3];
            dob = arg0[4];
            mobile = arg0[5];
            address = arg0[6];
            suburb = arg0[7];
            state = arg0[8];
            country = arg0[9];
            postcode = arg0[10];
            emergencyContact = arg0[11];
            emergencyNumber = arg0[12];
            String link = Connection.url + "/signup.php";
            URL url = new URL(link);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/d");
            String data = URLEncoder.encode("fname", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8");
            data += "&" + URLEncoder.encode("sname", "UTF-8") + "=" + URLEncoder.encode(sname, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");
            data += "&" + URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(dob, "UTF-8");
            data += "&" + URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8");
            data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
            data += "&" + URLEncoder.encode("suburb", "UTF-8") + "=" + URLEncoder.encode(suburb, "UTF-8");
            data += "&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(state, "UTF-8");
            data += "&" + URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(country, "UTF-8");
            data += "&" + URLEncoder.encode("postcode", "UTF-8") + "=" + URLEncoder.encode(postcode, "UTF-8");
            data += "&" + URLEncoder.encode("emergencyContact", "UTF-8") + "=" + URLEncoder.encode(emergencyContact, "UTF-8");
            data += "&" + URLEncoder.encode("emergencyNumber", "UTF-8") + "=" + URLEncoder.encode(emergencyNumber, "UTF-8");
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
