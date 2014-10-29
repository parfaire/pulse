package com.santoso.pramudita.pulse.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.santoso.pramudita.pulse.Cover;
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
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private Context context;
    private String em,pw;
    private ProgressDialog loading;
    public Login(Context context) {
        this.context = context;
        loading = new ProgressDialog(context);
    }

    protected void onPreExecute(){
       loading.setTitle("Login");
       loading.setMessage("Connecting to server");
       loading.setCancelable(false);
       loading.setIndeterminate(true);
       loading.show();
    }
    @Override
    protected String doInBackground(String... arg0) {
        try{
            em = (String)arg0[0];
            pw = (String)arg0[1];
            String link = Connection.url+"/login.php";
            URL url = new URL(link);
            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(em, "UTF-8");
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
        if (loading.isShowing()) {
            loading.dismiss();
        }
        result = result.trim();
        if(result.equalsIgnoreCase("FALSE")) {
            Toast.makeText(context, "Username or password is wrong", Toast.LENGTH_SHORT).show();
        }else{
            prefs = context.getSharedPreferences("PULSE", Context.MODE_PRIVATE);
            editor = prefs.edit();
            editor.putString("email",em);
            editor.putString("password",pw);
            Log.e("A",result);
            String[] data = result.split(",");
            editor.putString("fname", data[0]);
            editor.putString("lname", data[1]);
            editor.putString("dob", data[2]);
            editor.putString("gender", data[3]);
            editor.putString("mobile", data[4]);
            editor.putString("address",data[5]);
            editor.putString("econtact",data[6]);
            editor.putString("enumb", data[7]);
            editor.putString("passcode", data[8]);
            editor.putString("picture", data[9]);
            editor.commit();
            ((Cover)context).changeUIwhenLogin();
            Intent i = new Intent(context, MainMenu.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}