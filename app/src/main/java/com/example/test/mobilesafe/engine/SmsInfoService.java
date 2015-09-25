package com.example.test.mobilesafe.engine;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.example.test.mobilesafe.domain.SmsInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 9/25/2015.
 */
public class SmsInfoService {
    Context context;

    public SmsInfoService(Context context) {
        this.context = context;
    }

    public List<SmsInfo> getSmsInfos() {
        Uri uri = Uri.parse("content://sms");
        ContentResolver resolver = context.getContentResolver();
        List<SmsInfo> smsInfos = new ArrayList<>();
        Cursor cursor = resolver.query
                (uri, new String[]{"_id", "address", "date", "type", "body"}, null, null, null);
        SmsInfo smsInfo;
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String address = cursor.getString(1);
            String date = cursor.getString(2);
            int type = cursor.getInt(3);
            String body = cursor.getString(4);
            smsInfo = new SmsInfo(id, date, address, body, type);
            smsInfos.add(smsInfo);
            smsInfo = null;
        }
        cursor.close();
        /*String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/smsbackup.xml";
        File file = new File(path);*/
        return smsInfos;
    }
}
