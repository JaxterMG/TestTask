package com.example.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.os.Parcelable;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
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
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    String title;
    Database db = null;
    Button btnTo;
    Button btnFrom;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.execute();
        setContentView(R.layout.activity_main);
        btnTo = (Button) findViewById(R.id.buttonTo);
        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent activityChangeIntent = new Intent(MainActivity.this, TerminalsScreen.class);

                activityChangeIntent.putExtra("tabNum", 1);

                MainActivity.this.startActivity(activityChangeIntent);
            }
        });
        btnFrom = (Button) findViewById(R.id.buttonFrom);
        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent activityChangeIntent = new Intent(MainActivity.this, TerminalsScreen.class);

                activityChangeIntent.putExtra("tabNum", 0);
                activityChangeIntent.putExtra("DataBase", (Parcelable) db);
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });

        btnSave = (Button) findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                System.out.println("Save");
            }
        });
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
                        JSONObject worktableObject =  term.getJSONObject("worktables");
                        JSONArray worktableArray = worktableObject.getJSONArray("worktable");
                        worktable = MakeWorktable(worktableArray);
                        JSONObject maps = term.getJSONObject("maps");
                        JSONObject largeMap = new JSONObject();
                        JSONObject mediumMap = new JSONObject();
                        JSONObject miniMap = new JSONObject();
                        if(maps.has("width")) {
                            largeMap = maps.getJSONObject("width").getJSONObject("960").getJSONObject("height").getJSONObject("960");
                            mediumMap = maps.getJSONObject("width").getJSONObject("640").getJSONObject("height").getJSONObject("640");
                            miniMap = maps.getJSONObject("width").getJSONObject("944").getJSONObject("height").getJSONObject("352");
                        }
                        else
                        {
                            //TODO: Влепить заглушку для картинок
                        }
                        StringBuilder mapsUrls = new StringBuilder();
                        if(largeMap.has("url"))
                        {
                            mapsUrls.append(largeMap.getString("url")).append("\n");
                        }
                        if(mediumMap.has("url")) {
                            mapsUrls.append(mediumMap.getString("url")).append("\n");
                        }
                        if(miniMap.has("url"))
                        {
                            mapsUrls.append(miniMap.getString("url"));
                        }






                        // maps_url = term.getString("maps");
                       db.InsertTerminal(cityName,name,latitude,longitude,recieveCargo,
                               giveoutCargo,defaultT,worktable, mapsUrls.toString());

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;




        }
    }
    String MakeWorktable(JSONArray worktableArray)
    {
        StringBuilder worktable = new StringBuilder();
        String department;
        String monday;
        String tuesday;
        String wednesday;
        String thursday;
        String friday;
        String saturday;
        String sunday;
        String timetable;


        for (int i = 0; i<worktableArray.length();i++)
        {
            try {
                JSONObject worktableObj = worktableArray.getJSONObject(i);
                department = worktableObj.getString("department");
                monday = worktableObj.getString("monday");
                tuesday = worktableObj.getString("tuesday");
                wednesday = worktableObj.getString("wednesday");
                thursday = worktableObj.getString("thursday");
                friday = worktableObj.getString("friday");
                saturday = worktableObj.getString("saturday");
                sunday = worktableObj.getString("sunday");
                timetable = worktableObj.getString("timetable");

                worktable.append("department: "+ department +"\n"+
                                 "monday: "+ monday +"\n"+
                                 "tuesday: "+ tuesday +"\n"+
                                 "wednesday: "+ wednesday +"\n"+
                                 "thursday: "+ thursday +"\n"+
                                 "friday: "+ friday +"\n"+
                                 "saturday: "+ saturday +"\n"+
                                 "sunday: "+ sunday +"\n"+
                                 "timetable: "+ timetable+ "\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        worktable.deleteCharAt(worktable.length()-1);
        return worktable.toString();
    }
}


