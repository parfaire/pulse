package com.santoso.pramudita.pulse.WebService;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gembloth on 9/30/2014.
 */
public class ChangePasscode extends AsyncTask<String,Void,String> {
    private SharedPreferences prefs;
    private Context context;
    private String em,newp;
    public ChangePasscode(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){

    }
    @Override
    protected String doInBackground(String... arg0) {
        try{
            prefs = context.getSharedPreferences("PULSE",Context.MODE_PRIVATE);
            em = prefs.getString("email","");
            newp = arg0[0];
            String link = Connection.url+"/changepasscode.php";
            URL url = new URL(link);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/d k:m:s");
            Date time = new Date();
            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(em, "UTF-8");
            data += "&" + URLEncoder.encode("new", "UTF-8") + "=" + URLEncoder.encode(newp, "UTF-8");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            Log.e("ChangeP", "ChangeP of :" + em);
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }
            return sb.toString();
        }catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result){

    }
}