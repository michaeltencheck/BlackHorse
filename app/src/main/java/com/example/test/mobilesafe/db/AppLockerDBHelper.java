package com.example.test.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by test on 9/20/2015.
 */
public class AppLockerDBHelper extends SQLiteOpenHelper{
    private Context context;
    public AppLockerDBHelper(Context context) {
        super(context, "applocker.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE applocked (_id integer primary key autoincrement, packagename varchar(40))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
