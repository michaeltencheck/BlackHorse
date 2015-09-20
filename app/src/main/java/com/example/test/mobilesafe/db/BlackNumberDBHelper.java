package com.example.test.mobilesafe.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by test on 9/20/2015.
 */
public class BlackNumberDBHelper extends SQLiteOpenHelper{
    private Context context;
    public BlackNumberDBHelper(Context context) {
        super(context, "blacknumber.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE blacknumber (_id integer primary key autoincrement, number varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
