package com.example.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    String title;
    TextView tv;
    Database db = null;
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


    private class DownloadFile extends AsyncTask<Void,Void,Void>
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

    private  class ParseTextFile extends  AsyncTask<String, Void, String>
    {

        protected  void  onPreExecute()
        {
            db = new Database(getApplicationContext());
        }

        @Override
        protected String doInBackground(String... strings) {

            String jsontext = null;
            byte[] buffer = null;
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),strings[0]);
            try {
                InputStream is = new FileInputStream(file);
                int size = is.available();
                buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsontext = new String(buffer, "UTF-8");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsontext);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {

                JSONArray cities = null;

            cities = jsonObject.getJSONArray("city");



               for (int i = 0; i<cities.length();i++)
                {
                    String city = null;
                    String name = null;
                    double latitude = 0;
                    double longitude = 0;
                    boolean recieveCargo = false;
                    boolean giveoutCargo = false;
                    boolean defaultT = false;
                    String worktable = null;
                    String maps_url = null;



                    JSONObject object = cities.getJSONObject(i);
                    String cityName= object.getString("name");
                    JSONObject terminals = object.getJSONObject("terminals");
                    JSONArray terminal = terminals.getJSONArray("terminal");

                    for (int j = 0;j<terminal.length(); j++)
                    {
                        JSONObject term = terminal.getJSONObject(j);
                        name = term.getString("name");
                        latitude = term.getDouble("latitude");
                        longitude = term.getDouble("longitude");
                        recieveCargo = term.getBoolean("receiveCargo");
                        giveoutCargo = term.getBoolean("giveoutCargo");
                        defaultT = term.getBoolean("default");
                        //worktable = term.getString("worktable");
                        // maps_url = term.getString("maps");
                       db.InsertTerminal(cityName,name,latitude,longitude,recieveCargo,
                               giveoutCargo,defaultT,"worktable","efefef");

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;




        }
    }
}


