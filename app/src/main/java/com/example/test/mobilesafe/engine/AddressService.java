package com.example.test.mobilesafe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Created by test on 9/16/2015.
 */
public class AddressService {
    public static String getAddress(String number) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/address.db";
        String address = "该号码无法查询";
        int len = number.length();
        String pattern = "^9[3458]\\d{9}$";
        if (number.matches(pattern)) {
            SQLiteDatabase database = AddressDao.getDb(path);
            if (database.isOpen()) {
                Cursor cursor = database.rawQuery("select city from info where mobileprefix=?",
                        new String[]{number.substring(0,7)});
                if (cursor.moveToNext()) {
                    address = cursor.getString(0);
                }
                cursor.close();
                database.close();
            }
        }else switch (len) {
            case 7:
                address = "本地号码";
                break;
            case 8:
                address = "本地号码";
                break;
            case 10:
                String area = number.substring(0, 3);
                SQLiteDatabase database = AddressDao.getDb(path);
                if (database.isOpen()) {
                    Cursor cursor = database.rawQuery("select city from info where area=? limit 1",
                            new String[]{area});
                    if (cursor.moveToNext()) {
                        address = cursor.getString(0);
                        cursor.close();
                        database.close();
                    }
                }
                break;
            case 11:
                String area113 = number.substring(0, 3);
                String area114 = number.substring(0, 4);
                String area117 = number.substring(0, 7);
                SQLiteDatabase database11 = AddressDao.getDb(path);
                if (database11.isOpen()) {
                    Cursor cursor113 = database11.rawQuery("select city from info where area=? limit 1",
                            new String[]{area113});
                    Cursor cursor114 = database11.rawQuery("select city from info where area=? limit 1",
                            new String[]{area114});
                    Cursor cursor117 = database11.rawQuery("select city from info where mobileprefix=?",
                            new String[]{area117});
                    if (cursor113.moveToNext()) {
                        address = cursor113.getString(0);
                        cursor113.close();
                        database11.close();
                    }else if (cursor114.moveToNext()) {
                        address = cursor114.getString(0);
                        cursor114.close();
                        database11.close();
                    }else if (cursor117.moveToNext()) {
                        address = cursor117.getString(0);
                        cursor117.close();
                        database11.close();
                    }
                }
                break;
            case 12:
                String area12 = number.substring(0, 4);
                SQLiteDatabase database12 = AddressDao.getDb(path);
                if (database12.isOpen()) {
                    Cursor cursor12 = database12.rawQuery("select city from info where area=? limit 1",
                            new String[]{area12});
                    if (cursor12.moveToNext()) {
                        address = cursor12.getString(0);
                        cursor12.close();
                        database12.close();
                    }
                }
                break;
            default:
                break;
        }
        return address;
    }
}
