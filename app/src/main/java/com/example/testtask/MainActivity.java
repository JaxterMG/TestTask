package com.example.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.URLUtil;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DownloadJson();
        setContentView(R.layout.activity_main);
    }

   

    void DownloadJson()
    {
        String url = getString(R.string.URL);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        String title = URLUtil.guessFileName(url,null,null);
        request.setTitle(title);
        request.setDescription("Downloading file");
        request.addRequestHeader("cookie", url);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

        Toast.makeText(MainActivity.this, "DownloadingStarted", Toast.LENGTH_SHORT).show();

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),title);
        if(file.exists())
        {
            Toast.makeText(MainActivity.this, "Exists", Toast.LENGTH_SHORT).show();
            ParseFile(file);
        }
        else
        {
            Toast.makeText(MainActivity.this, "No such file", Toast.LENGTH_SHORT).show();
        }
    }
    void ParseFile(File fileName)
    {
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }

            br.close();

            ConvertToJson(text);
        }
        catch (IOException e) {

        }
        fileName.delete();

    }
    void ConvertToJson(StringBuilder stringBuilder)
    {
        try
        {
            JSONArray mainObject = new JSONArray(stringBuilder.toString());

            for (int i = 0; i < mainObject.length(); i++)
            {
                JSONObject object = mainObject.getJSONObject(i);
                int id = object.getInt("id");

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }
}
