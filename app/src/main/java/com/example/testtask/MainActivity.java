package com.example.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.JsonReader;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    String title;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DownloadFile downloadFile = new DownloadFile();
        downloadFile.execute();
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text_view_id);
    }



    BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(MainActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
          ParseTextFile parse = new ParseTextFile();
          parse.execute(title);
        }
    };


    private  class DownloadFile extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            String url = getString(R.string.URL);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            title = URLUtil.guessFileName(url,null,null);
            request.setTitle(title);
            request.setDescription("Downloading file");
            request.addRequestHeader("cookie", url);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            //Toast.makeText(MainActivity.this, "DownloadingStarted", Toast.LENGTH_SHORT).show();
            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            return null;
        }
    }


    private class ParseTextFile extends  AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings) {
           /* int i = 0;
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),strings[0]);
            try {
                InputStream is = new FileInputStream(file);
                Scanner sc = new Scanner(is);
                //Reading line by line from scanner to StringBuffer
                StringBuffer sb = new StringBuffer();
                while(sc.hasNext()){
                    i++;
                    sb.append(sc.nextLine());
                    System.out.println(i+" " +sc.nextLine());
                }

                // ConvertToJson(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.delete();
            return null;

            */
            String jsontext = null;
            byte[] buffer = null;
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),strings[0]);
            try {
                InputStream is = new FileInputStream(file);
                buffer = new byte[is.available()];
            while (is.read(buffer) != -1);


            } catch (IOException e) {
                e.printStackTrace();
            }
            jsontext = new String(buffer);
            //System.out.println(jsontext);
            file.delete();

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsontext);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray cities = jsonObject.getJSONArray("city");
                System.out.println(cities.get(1));
                //tv.setText(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;




        }
    }
}


