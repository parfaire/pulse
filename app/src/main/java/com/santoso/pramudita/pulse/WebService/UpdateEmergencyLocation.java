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

/**
 * Created by Gembloth on 9/25/2014.
 */
public class UpdateEmergencyLocation extends AsyncTask<String,Void,String> {
    private Context context;
    private String logid,lng,lat;
    public UpdateEmergencyLocation(Context context){
        this.context = context;
    }

    protected void onPreExecute(){

    }
    @Override
    protected String doInBackground(String... arg0) {
        try{
            lat = arg0[0];
            lng = arg0[1];
            logid = arg0[2];
            String link = Connection.url+"/update.php";
            URL url = new URL(link);
            String data = URLEncoder.encode("lng", "UTF-8") + "=" + URLEncoder.encode(lng, "UTF-8");
            data += "&" + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8");
            data += "&" + URLEncoder.encode("logid", "UTF-8") + "=" + URLEncoder.encode(logid, "UTF-8");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            Log.e("UpdateWS","Updating the location of logid:"+logid+" ("+lat+","+lng+")");
            // Read Server Response
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
