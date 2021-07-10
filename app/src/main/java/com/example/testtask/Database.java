package com.example.testtask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

class Database extends SQLiteOpenHelper
{
    private  Context context;
    private static final String DB_NAME = "TestTaskDB";
    private static final int DB_VERSION = 1;

    public Database(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

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
    public void GetGiveOutTerminalList()
    {
        List<TerminalCell> list = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from terminals", null,null);
        cursor.moveToFirst();
        for (int i = 0; i< cursor.getColumnCount();i++)
        {
           // TerminalCell cell = new TerminalCell(cursor.getString());
           // list.add(cell);
           // cursor.moveToNext();
        }
    }
    public void GetReceiveTerminalList()
    {
        List<TerminalCell> list = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from terminals", null,null);
        cursor.moveToFirst();
        for (int i = 0; i< cursor.getColumnCount();i++)
        {
            // TerminalCell cell = new TerminalCell(cursor.getString());
            // list.add(cell);
            // cursor.moveToNext();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
