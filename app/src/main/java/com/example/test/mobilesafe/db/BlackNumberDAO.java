package com.example.test.mobilesafe.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by test on 9/20/2015.
 */
public class BlackNumberDAO {
    private static final String TAG = "BlackNumberDAO";
    private Context context;
    private BlackNumberDBHelper helper;

    public BlackNumberDAO(Context context) {
        this.context = context;
        helper = new BlackNumberDBHelper(context);
    }

    public boolean find(String number) {
        SQLiteDatabase database = helper.getReadableDatabase();
        if (database.isOpen()) {
            Cursor cursor = database.rawQuery
                    ("select number from blacknumber where number=?", new String[]{number});
            if (cursor.moveToNext()) {
                return true;
            }
            cursor.close();
            database.close();
        }
        return false;
    }

    public void add(String number) {
        if (find(number)) {
            return;
        }
        SQLiteDatabase database = helper.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL("insert into blacknumber (number) values (?)", new Object[]{number});
            database.close();
        }
    }

    public void delete(String number) {
        if (find(number)) {
            SQLiteDatabase database = helper.getWritableDatabase();
            database.execSQL("delete from blacknumber where number=?", new Object[]{number});
            database.close();
        } else {
            Log.i(TAG, "data does not exist.");
            return;
        }
    }

    public void update(String newNumber, String oldNumber) {
        SQLiteDatabase database = helper.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL("update blacknumber set number=? where number=?",
                    new Object[]{newNumber, oldNumber});
            database.close();
        }
    }
}
