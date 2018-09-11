package com.example.calorycountapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "AppBase.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB.Table.DB_PRODUCT_CREATE);
        db.execSQL(DB.Table.DB_ACTIVE_CREATE);
        db.execSQL(DB.Table.DB_TEMPORARY_CREATE);
        db.execSQL(DB.Table.DB_HISTORY_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
