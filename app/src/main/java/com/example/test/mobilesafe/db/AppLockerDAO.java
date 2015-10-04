package com.example.test.mobilesafe.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 9/20/2015.
 */
public class AppLockerDAO {
    private static final String TAG = "AppLockerDAO";
    private Context context;
    private AppLockerDBHelper helper;

    public AppLockerDAO(Context context) {
        this.context = context;
        helper = new AppLockerDBHelper(context);
    }

    public boolean find(String packagename) {
        SQLiteDatabase database = helper.getReadableDatabase();
        if (database.isOpen()) {
            Cursor cursor = database.rawQuery
                    ("select number from applocked where number=?", new String[]{packagename});
            if (cursor.moveToNext()) {
                return true;
            }
            cursor.close();
            database.close();
        }
        return false;
    }

    public void add(String packagename) {
        if (find(packagename)) {
            return;
        }
        SQLiteDatabase database = helper.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL("insert into applocked (number) values (?)", new Object[]{packagename});
            database.close();
        }
    }

    public void delete(String packagename) {
        if (find(packagename)) {
            SQLiteDatabase database = helper.getWritableDatabase();
            database.execSQL("delete from applocked where number=?", new Object[]{packagename});
            database.close();
        } else {
            Log.i(TAG, "data does not exist.");
            return;
        }
    }


    public List<String> findAll() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        if (database.isOpen()) {
            Cursor cursor = database.rawQuery("select packagename from applocked", null);
            while (cursor.moveToNext()) {
                String packagename = cursor.getString(0);
                list.add(packagename);
            }
            cursor.close();
            database.close();
            return list;
        }
        return null;
    }
}
