package com.santoso.pramudita.pulse.WebService;

import android.app.ProgressDialog;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Gembloth on 10/29/2014.
 */
public class UploadFile {
    private ProgressDialog dialog;
    private String uploadFilePath,uploadFileName,upLoadServerUri,fileName;
    //private Context ctx;
    int serverResponseCode=0;
    public UploadFile(String pathAndFn, String fn) {
        //this.ctx=ctx;
        //uploadFilePath="/mnt/sdcard/";
        uploadFilePath="";
        fileName=fn;
        uploadFileName=pathAndFn;
        upLoadServerUri=Connection.url + "/upload.php";
        /*dialog = new ProgressDialog(ctx);
        dialog.setTitle("Upload File");
        dialog.setMessage("Uploading file...");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();*/
        Log.e("A","UPLOADING");
        new Thread(new Runnable() {
            public void run() {
                uploadFile(uploadFilePath + "" + uploadFileName);
            }
        }).start();
    }

    public int uploadFile(String sourceFileUri) {
        //String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            //dialog.dismiss();
            Log.e("uploadFile", "Source File not exist :"
                    +uploadFilePath + "" + uploadFileName);
            //Toast.makeText(ctx,"Source file does not exist",Toast.LENGTH_SHORT).show();
            return 0;
        }
        else{
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name='uploaded_file';filename='"
                                + fileName + "'" + lineEnd);

                        dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){
                    Log.e("A","UPLOD COMPLIT");
                    //Toast.makeText(ctx, "File Upload Complete.",Toast.LENGTH_SHORT).show();
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            }catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();
                Log.e("Upload file to server Exception", "Exception : "+ e.getMessage(), e);
            }
            //dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }
}
