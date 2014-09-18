package com.santoso.pramudita.pulse.WebService;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.santoso.pramudita.pulse.MainMenu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Gembloth on 9/18/2014.
 */
public class Login extends AsyncTask<String,Void,String> {
    private Context context;
    public Login(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){

    }
    @Override
    protected String doInBackground(String... arg0) {
        try{
            String un = (String)arg0[0];
            String pw = (String)arg0[1];
            String link = "http://192.168.1.104:80/test.php";
            URL url = new URL(link);
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(un, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pw, "UTF-8");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
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
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, MainMenu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}