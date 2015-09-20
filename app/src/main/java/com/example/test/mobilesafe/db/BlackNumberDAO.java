package com.example.test.mobilesafe.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by test on 9/20/2015.
 */
public class BlackNumberDAO {
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
}
