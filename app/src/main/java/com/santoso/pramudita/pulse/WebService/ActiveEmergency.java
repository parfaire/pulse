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
 * Created by Gembloth on 10/6/2014.
 */
public class ActiveEmergency extends AsyncTask<String,Void,Void> {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private Context context;
    private String em, lng, lat, trigger;

    public ActiveEmergency(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("PULSE", Context.MODE_PRIVATE);
        em = prefs.getString("email", "");
    }

    protected Void doInBackground(String... arg0) {
        try {
            lat = arg0[0];
            lng = arg0[1];
            trigger = arg0[2];
            String link = Connection.url + "/active.php";
            URL url = new URL(link);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/d k:m:s");
            Date time = new Date();
            String data = URLEncoder.encode("lng", "UTF-8") + "=" + URLEncoder.encode(lng, "UTF-8");
            data += "&" + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8");
            data += "&" + URLEncoder.encode("trigger", "UTF-8") + "=" + URLEncoder.encode(trigger, "UTF-8");
            data += "&" + URLEncoder.encode("em", "UTF-8") + "=" + URLEncoder.encode(em, "UTF-8");
            data += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(sdf.format(time), "UTF-8");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            Log.e("ActiveWS", "Activating logid:" + line + " (" + lat + "," + lng + ")");
            editor = prefs.edit();
            editor.putString("logid",line);
            editor.commit();
        } catch (Exception e) {
        }
        return null;
    }
}