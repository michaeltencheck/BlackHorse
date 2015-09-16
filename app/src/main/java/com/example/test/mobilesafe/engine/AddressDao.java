package com.example.test.mobilesafe.engine;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by test on 9/16/2015.
 */
public class AddressDao {
    public static SQLiteDatabase getDb(String path) {
        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        return database;
    }
}
