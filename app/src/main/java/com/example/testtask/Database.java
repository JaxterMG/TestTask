package com.example.testtask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class Database extends SQLiteOpenHelper
{
    public  static Database Instance;
    private  Context context;
    private static final String DB_NAME = "TestTaskDB";
    private static final int DB_VERSION = 1;
    List<TerminalCell>giveList = new LinkedList<>();
    List<TerminalCell>receiveList = new LinkedList<>();


    public Database (@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

    }

    public  void ChangeInstance(Database db)
    {
        Instance = db;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE TERMINALS("
                 +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "CITY TEXT,"
                + "NAME TEXT,"
                + "LATITUDE REAL, "
                + "LONGITUDE REAL,"
                + "RECEIVECARGO BOOLEAN,"
                + "GIVEOUTCARGO BOOLEAN,"
                + "DEFAULTT BOOLEAN,"
                + "WORKTABLE TEXT,"
                + "MAPSURL TEXT);");




    }

    public void InsertTerminal(String city ,String name,
                                       double latitude, double longitude,
                                       boolean receiveCargo, boolean giveoutCargo,
                                       boolean defaultT,
                                       String worktable, String maps_url)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues terminalValues = new ContentValues();
        terminalValues.put("CITY", city);
        terminalValues.put("NAME", name);
        terminalValues.put("LATITUDE", latitude);
        terminalValues.put("LONGITUDE", longitude);
        terminalValues.put("RECEIVECARGO", receiveCargo);
        terminalValues.put("GIVEOUTCARGO", giveoutCargo);
        terminalValues.put("DEFAULTT", defaultT);
        terminalValues.put("WORKTABLE", worktable);
        terminalValues.put("MAPSURL", maps_url);
        db.insert("TERMINALS", null, terminalValues);


    }

    public  List<TerminalCell> GetReceiveList()
    {
        return receiveList;
    }
    public  List<TerminalCell> GetGiveOutList()
    {
        return  giveList;
    }

    public  void TerminalsRequest()
    {
        GetTerminalList getTerminalList = new GetTerminalList();
        getTerminalList.execute();
    }

    private class GetTerminalList extends AsyncTask<Void, Void, String>
    {

        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(Void... voids) {
            int receives = 0;
            int giveOuts = 0;
            SQLiteDatabase db = Instance.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from terminals", null, null);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                double latitude = cursor.getDouble(cursor.getColumnIndex("LONGITUDE"));
                double longitude = cursor.getDouble(cursor.getColumnIndex("LATITUDE"));
                boolean receiveCargo = cursor.getInt(cursor.getColumnIndex("RECEIVECARGO")) > 0;
                boolean giveoutCargo = cursor.getInt(cursor.getColumnIndex("GIVEOUTCARGO")) > 0;
                boolean defaultT = cursor.getInt(cursor.getColumnIndex("DEFAULTT")) > 0;
                String city = cursor.getString(cursor.getColumnIndex("CITY"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                String worktable = cursor.getString(cursor.getColumnIndex("WORKTABLE"));
                String maps_url = cursor.getString(cursor.getColumnIndex("MAPSURL"));
                System.out.println(city);

                TerminalCell cell = new TerminalCell(city, name, latitude, longitude, receiveCargo, giveoutCargo, defaultT, worktable, maps_url);
                if (receiveCargo) {
                    receives++;
                    receiveList.add(cell);
                }
                if (giveoutCargo) {
                    giveOuts++;
                    giveList.add(cell);
                }
                cursor.moveToNext();
            }
            System.out.println("Recieves " + receives);
            return null;
        }
    }



        @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
